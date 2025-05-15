package testBS;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int[] nums = {8, 16, 40, 66, 69, 89, 12, 2, 5};
    private static final int BAR_WIDTH = 30;
    private static final int SCALE = 2;

    private final Label timeLabel = new Label("Tempo de ordenação: ...");

    @Override
    public void start(Stage primaryStage) {
        HBox barsContainer = new HBox(5);
        barsContainer.setAlignment(Pos.BOTTOM_CENTER);

        VBox[] barStacks = new VBox[nums.length]; // Hold label+rectangle pairs
        Rectangle[] bars = new Rectangle[nums.length];
        Label[] valueLabels = new Label[nums.length];

        for (int i = 0; i < nums.length; i++) {
            Rectangle bar = new Rectangle(BAR_WIDTH, nums[i] * SCALE);
            bar.setFill(Color.CORNFLOWERBLUE);
            Label valueLabel = new Label(String.valueOf(nums[i]));
            valueLabel.setStyle("-fx-font-size: 14px;");

            VBox stack = new VBox(5, valueLabel, bar);
            stack.setAlignment(Pos.BOTTOM_CENTER);

            barStacks[i] = stack;
            bars[i] = bar;
            valueLabels[i] = valueLabel;

            barsContainer.getChildren().add(stack);
        }

        timeLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #444;");

        VBox root = new VBox(20, barsContainer, timeLabel);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bubble Sort Visualizer");
        primaryStage.show();

        new Thread(() -> bubbleSortVisual(bars, valueLabels)).start();
    }

    private void bubbleSortVisual(Rectangle[] bars, Label[] labels) {
        try {
            long startTime = System.nanoTime();

            int n = bars.length;
            for (int i = 0; i < n; i++) {
                for (int j = 1; j < n; j++) {
                    if (nums[j - 1] > nums[j]) {
                        // Swap in data
                        int temp = nums[j - 1];
                        nums[j - 1] = nums[j];
                        nums[j] = temp;

                        // Swap visuals
                        int finalJ = j;
                        Platform.runLater(() -> {
                            // Swap heights
                            double tempHeight = bars[finalJ - 1].getHeight();
                            bars[finalJ - 1].setHeight(bars[finalJ].getHeight());
                            bars[finalJ].setHeight(tempHeight);

                            // Swap label text
                            String tempText = labels[finalJ - 1].getText();
                            labels[finalJ - 1].setText(labels[finalJ].getText());
                            labels[finalJ].setText(tempText);
                        });

                        Thread.sleep(300);
                    }
                }
            }

            long endTime = System.nanoTime();
            double elapsed = (endTime - startTime) / 1_000_000_000.0;

            Platform.runLater(() ->
                timeLabel.setText(String.format("Tempo de ordenação: %.3f seconds", elapsed))
            );

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

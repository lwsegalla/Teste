package quickSort;

public class teste_qs {
    
    // Função principal para chamar o quicksort
    public static void quickSort(int[] array, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = particionar(array, inicio, fim);
            quickSort(array, inicio, indicePivo - 1);  // Ordena a parte da esquerda
            quickSort(array, indicePivo + 1, fim);     // Ordena a parte da direita
        }
    }

    // Função para particionar o array e encontrar a posição correta do pivô
    private static int particionar(int[] array, int inicio, int fim) {
        int pivo = array[fim]; // Escolhe o último elemento como pivô
        int i = inicio - 1;    // Índice do menor elemento

        for (int j = inicio; j < fim; j++) {
            // Se o elemento atual for menor que o pivô
            if (array[j] < pivo) {
                i++;
                // Troca array[i] e array[j]
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // Troca array[i+1] com o pivô (array[fim])
        int temp = array[i + 1];
        array[i + 1] = array[fim];
        array[fim] = temp;

        return i + 1; // Retorna a posição do pivô
    }

    // Função para imprimir o array
    public static void imprimirArray(int[] array) {
        for (int valor : array) {
            System.out.print(valor + " ");
        }
        System.out.println();
    }

    // Programa principal
    public static void main(String[] args) {
        int[] array = {10, 7, 8, 9, 1, 5};
        
        System.out.println("Array original:");
        imprimirArray(array);
        
        quickSort(array, 0, array.length - 1);
        
        System.out.println("Array ordenado:");
        imprimirArray(array);
    }
}


import java.util.Scanner;

public class RepartoBloques {

    // Clase interna que representa el trabajo que realizará cada hilo
    static class TrabajoBloques implements Runnable {
        private int threadId; // Identificador del hilo (0, 1, 2, ...)
        private int N;        // Cantidad total de elementos/trabajo
        private int M;        // Número total de hilos

        public TrabajoBloques(int threadId, int N, int M) {
            this.threadId = threadId;
            this.N = N;
            this.M = M;
        }

        @Override
        public void run() {

            System.out.println("Hilo "+ threadId + " iniciado.");

            // Calculamos el tamaño del bloque de trabajo para cada hilo
            // (redondeo hacia arriba para asegurar que todos los elementos se asignen)
            int blockSize = (N + M - 1) / M;

            // Índice inicial del bloque de trabajo para este hilo
            int ini = blockSize * threadId;

            // Índice final del bloque de trabajo
            // Math.min evita que el último hilo exceda N
            int fin = Math.min(ini + blockSize, N);

            // Cada hilo procesa únicamente los índices que le corresponden
            for (int i = ini; i < fin; i++) {
                int cuadrado = i * i; // Trabajo: calcular el cuadrado del índice
                System.out.println("Hilo " + threadId + " -> índice " + i + " -> cuadrado = " + cuadrado);
            }

            // Mensaje opcional indicando que el hilo ha terminado su trabajo
            System.out.println("Hilo " + threadId + " ha terminado su bloque.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario la cantidad total de trabajo
        System.out.print("Introduce un número entero positivo (cantidad total de trabajo a realizar): ");
        int N = scanner.nextInt();

        // Solicitar al usuario el número de hilos
        System.out.print("Introduce el número de hilos(M): ");
        int M = scanner.nextInt();

        // Array para almacenar las referencias a los hilos
        Thread[] hilos = new Thread[M];

        // Crear y arrancar cada hilo con su bloque de trabajo correspondiente
        for (int i = 0; i < M; i++) {
            hilos[i] = new Thread(new TrabajoBloques(i, N, M));
            hilos[i].start();
        }

        // Esperar a que todos los hilos terminen antes de continuar
        for (int i = 0; i < M; i++) {
            try {
                hilos[i].join(); // join() bloquea el hilo principal hasta que cada hilo termine
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Mensaje final indicando que todos los hilos han terminado
        System.out.println("Todos los hilos han terminado.");
    }
}



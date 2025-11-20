import java.util.Scanner;

/*
 * Ejercicio 1: Reparto Cíclico
 *  Solicita al usuario que introduzca un número entero positivo N, que representa la
 *  cantidad total de trabajo a realizar.
 *  Pide al usuario que ingrese el número de hilos M que se ejecutarán para realizar el
 *  trabajo.
 *  Implementa el enfoque de reparto cíclico para dividir la carga de trabajo entre los M
 *  hilos. Cada hilo debe imprimir los índices del trabajo que le corresponden y su
 *  cuadrado.
 */

public class RepartoCiclico {

    /*
     * Clase interna que representa la tarea que ejecutará cada hilo.
     * Implementa Runnable para poder ser ejecutada dentro de un objeto Thread.
     */
    static class TrabajoCiclico implements Runnable {
        private int threadId; // Identificador del hilo (0,1,2,... M-1)
        private int N;        // Total de trabajo (cantidad de índices)
        private int M;        // Número total de hilos

        /*
         * Constructor que recibe los parámetros necesarios para asignar trabajo al hilo.
         */
        public TrabajoCiclico(int threadId, int N, int M) {
            this.threadId = threadId;
            this.N = N;
            this.M = M;
        }

        /*
         * Metodo que ejecutará cada hilo cuando llamemos a .start()
         */
        @Override
        public void run() {
            System.out.println("Hilo "+ threadId + " iniciado.");

            /*
             * Reparto cíclico:
             * Cada hilo comienza desde su ID (0,1,2,...)
             * y avanza saltando de M en M.
             *
             * Ejemplo: N=10, M=3
             * Hilo 0 → 0,3,6,9
             * Hilo 1 → 1,4,7
             * Hilo 2 → 2,5,8
             */
            for (int i = threadId; i < N; i += M){
                int cuadrado = i * i; // Simulación de trabajo: calcular cuadrado
                System.out.println("Hilo " + threadId + " -> índice " + i + " -> cuadrado = " + cuadrado);
            }

            System.out.println("Hilo " + threadId + " finalizado.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pedimos el total de trabajo N
        System.out.print("Introduce un número entero positivo (cantidad de trabajo): ");
        int N = scanner.nextInt();

        // Pedimos cuántos hilos se usarán
        System.out.print("Ingresa el número de hilos que se ejecutarán: ");
        int M = scanner.nextInt();

        // Array donde almacenaremos los objetos Thread
        Thread[] hilos = new Thread[M];

        /*
         * Creamos y arrancamos cada hilo.
         * Cada hilo recibe su ID correspondiente para poder calcular qué índices le pertenecen.
         */
        for (int i = 0; i < M; i++) {
            hilos[i] = new Thread(new TrabajoCiclico(i, N, M));
            hilos[i].start(); // Inicia la ejecución del hilo
        }

        /*
         * Esperamos a que todos los hilos terminen su trabajo usando join().
         * join() hace que el hilo principal espere hasta que cada hilo termine.
         */
        for (int i = 0; i < M; i++){
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // Fin del programa (solo cuando todos los hilos han acabado)
        System.out.println("Todos los hilos han terminado. Fin del programa.");
    }
}
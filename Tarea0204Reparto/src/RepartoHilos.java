import java.util.Scanner;

public class RepartoHilos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la cantidad total de trabajo (N): ");
        int N = scanner.nextInt();

        System.out.print("Introduce el número de hilos (M): ");
        int M = scanner.nextInt();

        Thread[] hilos = new Thread[M];

        // IMPLEMENTACIÓN POR BLOQUES
        System.out.println("COMIENZA LA IMPLEMENTACIÓN POR BLOQUES");

        for (int i=0; i < M; i++) {
            hilos[i] = new HiloBloques(M, N, i);
            hilos[i].start();
        }

        for (Thread hilo: hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // IMPLEMENTACIÓN CÍCLICA
        System.out.println("COMIENZA LA IMPLEMENTACIÓN CÍCLICA");

        for (int i=0; i < M; i++) {
            hilos[i] = new HiloCiclico(M, N, i);
            hilos[i].start();
        }

        for (Thread hilo: hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("FIN DEL PROGRAMA");
    }
}

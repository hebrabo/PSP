package Ej01Threads;

public class EjemploHilosThreads {
    public static void main(String[] args) {

        int NUM_HILOS = 8;
        Thread[] hilos = new Thread[NUM_HILOS];

        for (int i=0; i < NUM_HILOS; i++) {
            hilos[i] = new MiTareaThreads();
            hilos[i].start();
        }

        for (int i=1; i<=5; i++) {
            System.out.println("Soy el hilo principal y ejecuto el contador " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (Thread h: hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("FIN DEL PROGRAMA");
    }
}


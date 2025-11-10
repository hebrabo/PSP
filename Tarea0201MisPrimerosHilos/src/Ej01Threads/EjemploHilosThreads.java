package Ej01Threads;

public class EjemploHilosThreads {
    public static void main(String[] args) {

        // Crear 8 hilos
        MiTareaThreads hilo1 = new MiTareaThreads();
        MiTareaThreads hilo2 = new MiTareaThreads();
        MiTareaThreads hilo3 = new MiTareaThreads();
        MiTareaThreads hilo4 = new MiTareaThreads();
        MiTareaThreads hilo5 = new MiTareaThreads();
        MiTareaThreads hilo6 = new MiTareaThreads();
        MiTareaThreads hilo7 = new MiTareaThreads();
        MiTareaThreads hilo8 = new MiTareaThreads();

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();
        hilo6.start();
        hilo7.start();
        hilo8.start();

        // Hilo principal (main)
        for (int i = 1; i <= 5; i++) {
            System.out.println("Hilo principal: Contador " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Esperar a que todos terminen
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
            hilo5.join();
            hilo6.join();
            hilo7.join();
            hilo8.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Programa finalizado");
    }
}


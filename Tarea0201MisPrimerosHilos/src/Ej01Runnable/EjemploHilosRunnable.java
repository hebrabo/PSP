package Ej01Runnable;

public class EjemploHilosRunnable {
    public static void main(String[] args) {

        // Creamos 8 instancias de la clase MiTareaRunnable
        Runnable tarea1 = new MiTareaRunnable();
        Runnable tarea2 = new MiTareaRunnable();
        Runnable tarea3 = new MiTareaRunnable();
        Runnable tarea4 = new MiTareaRunnable();
        Runnable tarea5 = new MiTareaRunnable();
        Runnable tarea6 = new MiTareaRunnable();
        Runnable tarea7 = new MiTareaRunnable();
        Runnable tarea8 = new MiTareaRunnable();

        // Creamos 8 objetos Thread y les pasamos las instancias Runnable
        Thread hilo1 = new Thread(tarea1);
        Thread hilo2 = new Thread(tarea2);
        Thread hilo3 = new Thread(tarea3);
        Thread hilo4 = new Thread(tarea4);
        Thread hilo5 = new Thread(tarea5);
        Thread hilo6 = new Thread(tarea6);
        Thread hilo7 = new Thread(tarea7);
        Thread hilo8 = new Thread(tarea8);

        // Iniciamos los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();
        hilo6.start();
        hilo7.start();
        hilo8.start();

        // El hilo principal (main) también realiza algunas tareas
        for (int i = 1; i<= 5; i++){
            System.out.println("Hilo principal: Contador " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            // Esperamos a que todos los hilos terminen
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

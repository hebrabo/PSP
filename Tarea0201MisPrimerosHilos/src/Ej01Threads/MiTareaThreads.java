package Ej01Threads;

public class MiTareaThreads extends Thread {
    public void run() {
        for (int i=1; i<=5; i++){
            // getId() deprecated
            // System.out.println("Hilo" + Thread.currentThread().getId() + " : Contador " + i);
            System.out.println("Soy el hilo " + currentThread().threadId() + " y ejecuto el contador " + i);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

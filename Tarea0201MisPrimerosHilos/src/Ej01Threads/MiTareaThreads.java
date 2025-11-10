package Ej01Threads;

public class MiTareaThreads extends Thread {
    public void run() {
        for (int i=1; i<=5; i++){
            System.out.println("Hilo" + Thread.currentThread().getId() + " : Contador " + i);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

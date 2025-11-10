public class PrioridadRunnable implements Runnable {
    @Override
    public void run () {
        System.out.println("Hilo " + Thread.currentThread().getName() + " con prioridad " + Thread.currentThread().getPriority());
    }
}




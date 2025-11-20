import java.text.DecimalFormat;

public class PrioridadThread implements Runnable {
    private double contador; // double para no pasarnos del valor
    private Thread thread;

    public PrioridadThread(int priority) {
        this.contador = 0;
        this.thread = new Thread(this);
        this.thread.setPriority(priority);
        this.thread.setName("Hilo-" + priority);
    }

    @Override
    public void run() {
        while (true) {
            // hacemos que el hilo trabaje para nada
            for (int i = 0; i < 100; ) {
                i++;
            }
            // contamos como interación
            this.contador++;
        }
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return "PrioridadThread ["
                + thread.getName() + "]"
                + "prioridad[" + thread.getPriority() + "]"
                + "contador = " + df.format(this.contador) + "]";
    }

    public void start() {
        thread.start();
    }

    public void join() throws InterruptedException {
        thread.join();
    }
}

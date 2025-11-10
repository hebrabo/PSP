public class PrimoRunnable implements Runnable {
    private int inicio;
    private int fin;

    public PrimoRunnable(int inicio, int fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public void run() {
        for (int i = inicio; i <= fin; i++) {
            if (PrimosConcurrente.esPrimo(i)) {
                // Opcional: System.out.println(i + " es primo");
            }
        }
    }
}

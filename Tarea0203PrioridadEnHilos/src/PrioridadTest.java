public class PrioridadTest {

    public static void main(String[] args) {
        System.out.println("Pruebas de prioridad en threads");
        System.out.println(Thread.MIN_PRIORITY + " - Prioridad más Baja");
        System.out.println(Thread.MAX_PRIORITY + " - Prioridad más Alta");
        PrioridadThread[] pt = new PrioridadThread[Thread.MAX_PRIORITY];

        for (int t = Thread.MIN_PRIORITY; t <= Thread.MAX_PRIORITY; t++) {
            pt[t - 1] = new PrioridadThread(t);
            pt[t - 1].start();
        }

        try {
            while (true) {
                System.out.println("======================================");
                for (PrioridadThread t : pt) {
                    System.out.println(t.toString());
                }
                System.out.println("======================================");
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

public class PrimosConcurrente {

    // Reutilizamos el metodo esPrimo del programa secuencial
    public static boolean esPrimo(int numero) {
        if (numero <= 1) return false;
        for (int i = 2; i < numero; i++) {
            if (numero % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("Comenzando ejecución concurrente...");

        long inicio = System.currentTimeMillis();

        Thread hilo1 = new Thread(new PrimoRunnable(1, 100000));
        Thread hilo2 = new Thread(new PrimoRunnable(100001, 200000));
        Thread hilo3 = new Thread(new PrimoRunnable(200001, 300000));
        Thread hilo4 = new Thread(new PrimoRunnable(300001, 400000));

        // Iniciar hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();

        // Esperar a que terminen
        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo total (concurrente): " + (fin - inicio) + " ms");
    }
}

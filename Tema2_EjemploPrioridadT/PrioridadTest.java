
public class PrioridadTest {



	public static void main(String[] args) {
		System.out.println("Pruebas de prioridad en threads");
		System.out.println(Thread.MIN_PRIORITY + " - Prioridad más Baja");
		System.out.println(Thread.MAX_PRIORITY + " - Prioridad más Alta");

        /*
         * - Crea un array de objetos PrioridadThread con tamaño igual a Thread.MAX_PRIORITY (que es 10).
         * - Recorre las prioridades de Java de 1 (MIN_PRIORITY) a 10 (MAX_PRIORITY).
         * - Para cada prioridad t, crea un PrioridadThread y lo inicia.
         * Resultado: se lanzan 10 hilos, cada uno con una prioridad distinta.
        */

        PrioridadThread[] pt = new PrioridadThread[Thread.MAX_PRIORITY];

		for (int t = Thread.MIN_PRIORITY; t <= Thread.MAX_PRIORITY; t++) {
			pt[t - 1] = new PrioridadThread(t);
			pt[t - 1].start();
		}

        /*
        * - Bucle infinito que cada 5 segundos imprime el contador de cada hilo.
        * - Permite ver cuál hilo ha "trabajado más" hasta ese momento.
        * - Esto refleja la influencia de la prioridad en la ejecución del planificador de hilos.
         */

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

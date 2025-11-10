
import java.text.DecimalFormat;

public class PrioridadThread implements Runnable {

    /*
    * - Cada objeto PrioridadThread envuelve un hilo real (Thread).
    * - Se asigna un nombre y una prioridad al hilo.
    * - contador cuenta cuántas iteraciones ha realizado el hilo.
     */

	private double contador; // double para no pasarnos del valor
	private Thread thread;

	public PrioridadThread(int priority) {
		this.contador = 0;
		this.thread = new Thread(this);
		this.thread.setPriority(priority);
		this.thread.setName("Hilo-" + priority);
	}

    /*
    * - Bucle infinito que hace trabajo inútil (i++ hasta 100) para simular carga de trabajo.
    * - Cada vez que termina el mini-bucle de 100 iteraciones, aumenta contador en 1.
    * - Hilo de mayor prioridad, en teoría, debería ejecutar más veces contador que los de menor prioridad.
     */

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

    /*
    * - Devuelve un texto descriptivo del hilo: nombre, prioridad y cuántas iteraciones ha hecho.
    * - Se usa en PrioridadTest para imprimir el estado de todos los hilos cada 5 segundos.
     */
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
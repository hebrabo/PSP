public class MainTicTac {
    public static void main(String[] args) {
        // Hilo TIC con prioridad mínima
        Thread hiloTIC = new Thread(new TicTacRunnable(" TIC "));
        hiloTIC.setPriority(Thread.MIN_PRIORITY);

        // Hilo TAC con prioridad mínima
        Thread hiloTAC = new Thread(new TicTacRunnable(" TAC "));
        hiloTAC.setPriority(Thread.MAX_PRIORITY);

        // Iniciamos ambos hilos
        hiloTIC.start();
        hiloTAC.start();
    }
}

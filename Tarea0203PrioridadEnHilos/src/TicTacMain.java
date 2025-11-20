class TicTacPrinter implements Runnable {
        private final String word;

        public TicTacPrinter(String word) {
            this.word = word;
        }
        @Override
        public void run() {
            while(true) {
                System.out.print(word);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public class TicTacMain {

    public static void main(String[] args) {
        // Hilo TIC con prioridad mínima
        Thread ticThread = new Thread(new TicTacPrinter("TIC "));
        Thread tacThread = new Thread(new TicTacPrinter("TAC "));

        ticThread.setPriority(Thread.MAX_PRIORITY);
        tacThread.setPriority(Thread.MIN_PRIORITY);

        ticThread.start();
        tacThread.start();
    }
}

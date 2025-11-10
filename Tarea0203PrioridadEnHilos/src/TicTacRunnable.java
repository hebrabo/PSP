public class TicTacRunnable implements Runnable{
    private String mensaje;

    public TicTacRunnable(String mensaje){
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(mensaje);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
}

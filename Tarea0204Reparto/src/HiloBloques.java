public class HiloBloques extends Thread {
    int numHilos, numFinal, id;

    public HiloBloques(int numHilos, int numFinal, int id) {
        this.numHilos = numHilos;
        this.numFinal = numFinal;
        this.id = id;
    }

    @Override
    public void run() {
        int blockSize = (numFinal + numHilos - 1)/ numHilos;
        int startIndex = blockSize * id;
        int endIndex = Math.min(startIndex + blockSize, numFinal);

        for(int i = startIndex; i < endIndex; i++) {
            System.out.println("Soy el hilo " + id + " digo el num " + i + " y el cuadrado es " + (i*i));
        }
    }
}



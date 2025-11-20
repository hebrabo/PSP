
public class HiloCiclico extends Thread {
    int numHilos, numFinal, id;

    public HiloCiclico(int numHilos, int numFinal, int id) {
        this.numHilos = numHilos;
        this.numFinal = numFinal;
        this.id = id;
    }

    @Override
    public void run(){
        for (int i = id; i < numFinal; i+=numHilos) {
            System.out.println("Soy el hilo " + id + " digo el num " + i + " y el cuadrado es " + (i*i));
        }
    }
}
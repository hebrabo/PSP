class CompruebaPrimo extends Thread {
    int inicio;
    int fin;
    public CompruebaPrimo(int incio, int fin){
        super();
        this.inicio = incio;
        this.fin = fin;
    }

    public void run() {
        for (int i = this.inicio; i <= this.fin; i++){
            boolean resultado = PrimosHilos.esPrimo(i);
            if (resultado) {
                System.out.println("El número " + i + " es primo.");
            } else {
                System.out.println("El número " + i + " no es primo.");
            }
        }
    }
}

public class PrimosHilos {
    public static boolean esPrimo(int numero) {
        //Los números menores o iguales a 1 no son primos
        if (numero <= 1) {
            return false;
        }
        // Comprobamos si es divisible por algún númerp desde 2 hasta número - 1
        for (int i = 2; i < numero; i++) {
            if (numero % i ==0) {
                return false; // Si es divisible, no es primo
            }
        }
        return true; // Si no es divisible por ningún número, es primo
    }

    public static void main(String[] args) {
        long tiempoInicio = System.currentTimeMillis();

        Thread[] hilos = new Thread[4];

        hilos[0] = new CompruebaPrimo(1, 100000);
        hilos[1] = new CompruebaPrimo(100001, 200000);
        hilos[2] = new CompruebaPrimo(200001,  300000);
        hilos[3] = new CompruebaPrimo(300001, 400000);

        for (Thread hilo: hilos) {
            hilo.start();
        }
        for (Thread hilo: hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        long tiempoFin = System.currentTimeMillis();
        System.out.println("El programa ha tardado " + (tiempoFin - tiempoInicio)/1000.0);
    }
}

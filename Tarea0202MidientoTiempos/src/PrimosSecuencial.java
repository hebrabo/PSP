/*
* Ejercicio: Midiendo tiempos
* Crea dos programas en Java que muestren por pantalla los números del 1 al 400,000
* (inclusive) y determinen si cada número es primo o no. En la primera parte, implementarás la
* solución de manera secuencial.
* Mide el tiempo de ejecución del programa utilizando System.currentTimeMillis().
 */

public class PrimosSecuencial {

    // Metodo para determinar si un número es primo
    public static boolean esPrimo(int numero){
        // Los números menores o iguales a 1 no son primos
        if (numero <= 1) {
            return false;
        }
        // Comprobamos si es divisible por algún número desde 2 hasta 'número- 1'
        for (int i = 2; i < numero; i++) {
            if (numero % i == 0) {
                return false; // Si es divisible, no es primo
            }
        }
        return true; // Si no es divisible por ningún número, es primo
    }

    public static void main(String[] args) {

        System.out.println("Comenzando ejecución secuencial...");

        // Guardamos el tiempo inicial
        long inicio = System.currentTimeMillis();

        // Recorremos los números del 1 al 400000
        for (int i = 1; i <= 400000; i++) {
            esPrimo(i);
        }

        // Guardamos el tiempo final
        long fin = System.currentTimeMillis();

        // Mostramos el tiempo total de ejecución
        System.out.println("Tiempo total (secuencial): " + (fin - inicio) + " ms");
    }
}

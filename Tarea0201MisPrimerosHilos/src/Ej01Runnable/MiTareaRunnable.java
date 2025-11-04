package Ej01Runnable;

/* Ejercicio: Mis primeros hilos
 * Crear dos programas en Java que utilice hilos para ejecutar tareas concurrentes.
 * Ej01Runnable implementará la concurrencia implementando la clase Runnable,
 * mientras que Ej01Threads lo hará extendiendo la clase Thread.
 * Sigue estos pasos:
 * 1. Crea una clase MiTarea, en su metodo run() debe haber un bucle que cuente del 1 al 5,
 * mostrando mensajes que incluyan el ID del hilo actual y el contador.
 * Entre cada una de las iteraciones simula que el hilo está realizando alguna tarea con Thread.sleep(1000);.
 * 2. En la clase principal EjemploHilos, en el metodo main, crea 8 hilos que ejecuten MiTarea.
 * 3. En el hilo principal (main):
 * a. Crea un bucle que cuente del 1 al 5 y muestre mensajes que incluyan el contador.
 * b. Agrega una pausa de 1 segundo entre cada iteración.
 * 4. Después del bucle principal, utiliza join() para esperar a que los hilos terminen.
 * 5. Finalmente, muestra un mensaje indicando que el programa ha finalizado.
 */
public class MiTareaRunnable implements Runnable{
    @Override
    public void run() {
        for (int i=1; i<=5; i++){
            System.out.println("Hilo" + Thread.currentThread().getId() + " : Contador " + i);
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



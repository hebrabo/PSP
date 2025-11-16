import java.util.HashMap;

/**
 * ATENCIÓN: código erróneo porque utiliza HashMap con hilos.
 *
 * Ejecútalo varias veces y observa el resultado. ¿Qué ocurre?
 */
public class EjemploHashMapConHilosErroneo {
    public static void main(String[] args) throws InterruptedException {
        HashMap<String, Integer> visitas = new HashMap<>();

        // Dos hilos que incrementan el contador de usuarios.
        // La parte () -> { ... } es una expresión lambda, equivalente a un Runnable.
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                visitas.merge("Alice", 1, Integer::sum);
                visitas.merge("Anonymous", 1, Integer::sum);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                visitas.merge("Bob", 1, Integer::sum);
                visitas.merge("Anonymous", 1, Integer::sum);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        visitas.forEach((usuario, contador) ->
                System.out.println(usuario + " -> " + contador + " visitas")
        );
    }
}


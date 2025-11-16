import java.util.concurrent.ConcurrentHashMap;

public class EjemploConcurrentHashMap {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Integer> visitas = new ConcurrentHashMap<>();

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


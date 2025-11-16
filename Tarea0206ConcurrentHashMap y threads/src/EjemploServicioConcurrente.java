import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * EjemploServicioConcurrente.java
 *
 * Ejemplo didáctico que simula un servicio multihilo que atiende solicitudes de clientes.
 * El objetivo es mostrar el uso de ConcurrentHashMap para mantener un contador por cliente
 * de forma segura en concurrencia.
 */
public class EjemploServicioConcurrente {

    // Mapa compartido entre hilos: clave = cliente, valor = número de solicitudes atendidas.
    // ConcurrentHashMap proporciona seguridad para accesos concurrentes sin bloquear todo el mapa.
    private static final ConcurrentHashMap<String, Integer> solicitudes = new ConcurrentHashMap<>();

    /**
     * Método que simula la atención de una solicitud de un cliente.
     * Se invoca desde múltiples hilos en paralelo.
     *
     * @param cliente nombre o id del cliente que realiza la solicitud
     */
    public static void atenderSolicitud(String cliente) {
        // merge realiza la operación atómicamente:
        // - si la clave no existe, inserta el valor 1
        // - si existe, aplica la función Integer::sum con el valor existente y el nuevo valor 1
        //
        // Esto evita condiciones de carrera que tendríamos si hiciéramos:
        // int v = solicitudes.getOrDefault(cliente, 0);
        // solicitudes.put(cliente, v + 1);
        solicitudes.merge(cliente, 1, Integer::sum);

        // Mensaje informativo para ver la concurrencia en la consola
        System.out.println("Atendiendo solicitud de " + cliente + " en " + Thread.currentThread().getName());

        try {
            // Simula tiempo de procesamiento variable por solicitud (0..499 ms).
            // En un servicio real esto sería la lógica de negocio (I/O, cálculo, acceso DB, etc.)
            Thread.sleep((long) (Math.random() * 500));
        } catch (InterruptedException e) {
            // Mantener el hilo con su estado de interrupción y salir limpiamente.
            Thread.currentThread().interrupt();
            System.err.println("Hilo " + Thread.currentThread().getName() + " interrumpido.");
        }
    }

    /**
     * Punto de entrada: crea un pool de hilos que simula la capacidad concurrente del servicio,
     * envía varias tareas y muestra un resumen con el conteo final por cliente.
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando servicio concurrente...\n");

        // 1) Creamos un pool de hilos con tamaño fijo (simula trabajadores del servicio).
        //    En un servicio real, la cantidad de hilos puede depender de CPU, I/O o configuración.
        final int poolSize = 4;
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        // 2) Definimos clientes simulados (podrían ser identificadores, IPs, nombres de usuario, etc.)
        String[] clientes = {"ClienteA", "ClienteB", "ClienteC", "ClienteD", "ClienteE"};

        // 3) Enviamos N tareas para simular N solicitudes entrantes.
        final int totalSolicitudes = 20;
        for (int i = 0; i < totalSolicitudes; i++) {
            // Selección round-robin del cliente para simular cargas variadas
            String cliente = clientes[i % clientes.length];

            // Submit envía la tarea al executor para que un hilo la ejecute cuando esté disponible.
            executor.submit(() -> atenderSolicitud(cliente));
        }

        // 4) Gestión de cierre: solicitamos al executor que no acepte nuevas tareas y esperamos.
        executor.shutdown(); // no acepta nuevas tareas, pero procesa las pendientes

        // 5) Esperamos hasta que terminen todas las tareas o expire el timeout (10 segundos en este caso).
        //    awaitTermination devuelve true si todas las tareas acabaron dentro del timeout.
        boolean finished = executor.awaitTermination(10, TimeUnit.SECONDS);
        if (!finished) {
            // Si no ha terminado en el tiempo razonable, forzamos el apagado.
            System.err.println("No se terminaron las tareas en tiempo, forzando shutdownNow()");
            executor.shutdownNow();
        }

        // 6) Mostrar resumen final: consultas por cliente
        System.out.println("\n=== Resumen de solicitudes atendidas ===");
        solicitudes.forEach((cliente, cantidad) ->
                System.out.println(cliente + " → " + cantidad + " solicitudes"));

        System.out.println("\nServicio finalizado.");
    }
}

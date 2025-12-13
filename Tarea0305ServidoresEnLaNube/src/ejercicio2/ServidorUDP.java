package ejercicio2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ServidorUDP {
    public static void main(String[] args) {
        int puerto = 5001;

        try {
            // 1. Crear el Socket UDP
            // A diferencia de TCP, aquí no hay "ServerSocket" y "Socket".
            // El DatagramSocket es como un buzón que sirve tanto para enviar como recibir.
            DatagramSocket socket = new DatagramSocket(puerto);
            System.out.println("Servidor de Primos (UDP) esperando en puerto " + puerto + "...");

            // 2. Buffer de recepción
            // Es el array de bytes donde se volcará la información que llegue.
            byte[] buffer = new byte[1024];

            while (true) {
                // 3. Preparamos el "sobre" (DatagramPacket) vacío
                // Le decimos al socket: "Usa este buffer para guardar lo que llegue".
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);

                // 4. Esperar petición (BLOQUEANTE)
                // El programa se detiene aquí hasta que alguien envía algo al puerto 5001.
                socket.receive(paqueteRecibido);

                // 5. Extraer información del cliente
                // En UDP no hay conexión persistente ("estado").
                // Cada paquete trae remitente (IP y Puerto) escrito en sus metadatos.
                // Debemos guardarlos para saber a quién responder luego.
                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                InetAddress direccionCliente = paqueteRecibido.getAddress();
                int puertoCliente = paqueteRecibido.getPort();

                System.out.println("Solicitud recibida de: " + direccionCliente + ":" + puertoCliente);

                // 6. GESTIÓN DE HILOS (Concurrency)
                // El cálculo de primos puede tardar. Si lo hacemos aquí, el servidor
                // estaría "sordo" a nuevas peticiones hasta terminar.
                // Por eso, creamos un hilo (Thread) y le pasamos los datos necesarios.
                // El hilo principal vuelve inmediatamente arriba al 'receive' para escuchar a otro.
                HiloPrimos tarea = new HiloPrimos(socket, direccionCliente, puertoCliente, mensaje);
                new Thread(tarea).start();

                // 7. Limpieza del buffer
                // Importante: Creamos un array nuevo para la siguiente vuelta.
                // Si no lo hacemos, podríamos tener "basura" de datos antiguos mezclada con nuevos.
                buffer = new byte[1024];
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// ---------------------------------------------------------
// CLASE HILOPRIMOS
// Realiza el procesamiento pesado del cálculo (buscar primos)
// en un hilo independiente para permitir la concurrencia.
// ---------------------------------------------------------
class HiloPrimos implements Runnable {
    // Necesitamos guardar una referencia al socket del servidor para poder responder
    private DatagramSocket socket;
    private InetAddress direccion; // ¿A quién respondo?
    private int puerto;            // ¿A qué puerto respondo?
    private String mensaje;        // ¿Qué número me han pedido?

    public HiloPrimos(DatagramSocket socket, InetAddress direccion, int puerto, String mensaje) {
        this.socket = socket;
        this.direccion = direccion;
        this.puerto = puerto;
        this.mensaje = mensaje;
    }

    // Lógica matemática simple para verificar primos
    private boolean esPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public void run() {
        try {
            // Convertimos el texto recibido a número
            // .trim() elimina espacios en blanco o saltos de línea sobrantes
            int numero = Integer.parseInt(mensaje.trim());
            List<String> listaPrimos = new ArrayList<>();

            // Realizamos el cálculo (esto es lo que podría tardar tiempo)
            for (int i = 2; i <= numero; i++) {
                if (esPrimo(i)) {
                    listaPrimos.add(String.valueOf(i));
                }
            }

            // Preparamos la respuesta (ej: "2,3,5,7")
            String respuesta = String.join(",", listaPrimos);
            byte[] datosEnviar = respuesta.getBytes();

            // 8. Empaquetar la respuesta
            // Creamos un nuevo "sobre" con los datos, dirigido a la IP/Puerto que guardamos al principio.
            DatagramPacket paqueteEnvio = new DatagramPacket(
                    datosEnviar,
                    datosEnviar.length,
                    direccion,
                    puerto
            );

            // 9. Enviar respuesta (Thread-Safe)
            // El socket es compartido por todos los hilos.
            // Aunque UDP es "connectionless", es buena práctica sincronizar el acceso al recurso
            // compartido (el socket) para evitar condiciones de carrera si dos hilos intentan
            // escribir en el buffer de red del sistema operativo exactamente al mismo tiempo.
            synchronized (socket) {
                socket.send(paqueteEnvio);
            }
            System.out.println("Primos enviados a " + direccion + ":" + puerto);

        } catch (NumberFormatException e) {
            System.out.println("Error: El cliente no envió un número válido.");
            // Aquí podríamos enviar un paquete de vuelta con el error si quisiéramos mejorar la UX
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

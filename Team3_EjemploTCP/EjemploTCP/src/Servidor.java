import java.io.*;
import java.net.*;

// Clase principal del servidor
public class Servidor {
    public static void main(String[] args) {
        try {
            // Crear un servidor que escucha en el puerto 12345.
            // Esto abre un "puerto" donde los clientes pueden intentar conectarse.
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando conexiones...");

            // Bucle infinito para permitir que múltiples clientes se conecten.
            // Cada conexión se maneja en un hilo independiente.
            while (true) {
                // Espera (bloqueante) hasta que un cliente se conecte.
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado.");

                // Crear un hilo (Thread) para manejar la comunicación con ese cliente.
                // Esto permite que el servidor atienda a muchos clientes simultáneamente.
                Thread clientThread = new Thread(new ClienteHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Clase que se ejecuta en un hilo y maneja la comunicación con un cliente específico
class ClienteHandler implements Runnable {
    private Socket clientSocket;

    // El socket se pasa desde el servidor cuando un cliente se conecta
    public ClienteHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            // Obtener el flujo de entrada (lo que envía el cliente)
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            // Leer un mensaje enviado por el cliente (hasta el salto de línea)
            String mensaje = in.readLine();
            System.out.println(mensaje);

            // Obtener el flujo de salida para enviar datos al cliente
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);

            // Enviar respuesta al cliente
            out.println("Hola, cliente!");

            // Cerrar conexión (cerra el socket) cuando termina la comunicación
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
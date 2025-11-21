import java.io.*;
import java.net.Socket;

// Clase principal del cliente
public class Cliente {
    public static void main(String[] args) {
        try {
            // Establecer conexión con el servidor.
            // "localhost" significa que el servidor está en la misma máquina.
            // 12345 es el puerto donde el servidor está escuchando.
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Conexion establecida con el servidor.");

            // Obtener flujo de salida para enviar datos al servidor
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);

            // Enviar un mensaje al servidor
            out.println("Hola, servidor!");

            // Obtener flujo de entrada para recibir la respuesta del servidor
            InputStream inputStream = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

            // Leer la respuesta del servidor
            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

            // Cerrar el socket después de la comunicación
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Servidor que escucha las conexiones en el puerto 12345
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando conexiones...");

            while (true) {
                // Espera (bloqueante) hasta que un cliente se conecte.
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado.");

                // Crear un hilo (Thread) para manejar la comunicación con ese cliente.
                // Esto permite que el servidor atienda a muchos clientes simultáneamente.
                Thread clientThread = new Thread(new ClienteHandler(clientSocket));
                clientThread.start();
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

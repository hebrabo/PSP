import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class UDPClient {
    public static void main(String[] args) {
        try {
            // Un DatagramSocket sin puerto indica que el SO asignará uno automáticamente.
            DatagramSocket socket = new DatagramSocket();

            // Dirección IP del servidor (localhost si se ejecuta en la misma máquina)
            InetAddress serverAddress = InetAddress.getByName("localhost"); // Direccion IP del servidor

            // Puerto del servidor donde enviamos los mensajes
            int serverPort = 12345; // Puerto del servidor

            // El mensaje que queremos enviar
            String message = "Hola, servidor!";
            byte[] sendData = message.getBytes();

            // El DatagramPacket es el "sobre" que enviamos: contiene los datos, la IP y el puerto de destino
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

            // Enviamos el paquete a través del socket
            socket.send(sendPacket);

            // Aqui puedes esperar una respuesta del servidor si es necesario
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
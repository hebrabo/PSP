import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer {
    public static void main(String[] args) {
        try {
            // Un DatagramSocket es el "punto" donde nuestro servidor escucha mensajes UDP.
            // Aquí lo abrimos en el puerto 12345.
            DatagramSocket socket = new DatagramSocket(12345); // Puerto del servidor

            // Buffer donde se almacenarán los datos que lleguen desde un cliente.
            byte[] receiveData = new byte[1024];

            while (true) {
                // DatagramPacket es una especie de "sobre" que contiene datos y metadatos (IP y puerto del remitente)
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Espera (bloqueante) hasta que llegue un paquete desde un cliente
                socket.receive(receivePacket);

                // Extraemos el mensaje recibido convirtiendo los bytes a un String
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Recibido desde el cliente: " + message);

                // Aqui puedes procesar el mensaje segun tus necesidades

                // Preparar y enviar una respuesta al cliente (si es necesario)
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
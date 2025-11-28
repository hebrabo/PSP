import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class CalculadoraUDPServidor {

    // Puerto en el que el servidor escuchará los mensajes UDP
    private static final int SERVER_PORT = 9876;


    public static void main(String[] args) {
        try {
            // Creamos un socket UDP en el puerto definido
            DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT);
            System.out.println("Servidor iniciando el puerto " + SERVER_PORT);

            // Bucle infinito para mantener el servidor escuchando
            while(true) {
                // Creamos un buffer para recibir datos del cliente
                byte[] receiveData = new byte[1024];
                // Creamos un paquete UDP que contendrá los datos recibidos
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                // Esperamos a recibir un paquete del cliente (bloqueante)
                serverSocket.receive(receivePacket);

                // Convertimos los bytes recibidos en una cadena
                String request = new String(receivePacket.getData(), 0, receivePacket.getLength());
                String[] parts = request.split(":");
                String operation = parts[0]; // Operación: SUMA o RESTA
                int num1 = Integer.parseInt(parts[1]); // Primer número
                int num2 = Integer.parseInt(parts[2]); // Segundo número

                // Inicializamos el resultado
                int result = 0;
                // Verificamos qué operación realizar
                if (operation.equals("SUMA")) {
                    result = num1 + num2;
                } else if (operation.equals("RESTA")){
                    result = num1 - num2;
                }

                // Preparamos la respuesta como cadena
                String response = "Resultado: " + result;
                // Creamos un paquete UDP de respuesta con la dirección y puerto del cliente
                DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length, receivePacket.getAddress(), receivePacket.getPort());

                // Enviamos el paquete de vuelta al cliente
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar errores en caso de fallo
        }
    }
}

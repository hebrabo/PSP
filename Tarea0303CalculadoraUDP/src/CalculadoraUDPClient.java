// Cliente UDP que se comunica con el servidor para realizar operaciones

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class CalculadoraUDPClient {

    // IP del servidor y puerto de comunicación
    private static final String SERVER_IP ="localhost";
    private static final int SERVER_PORT = 9876;

    public static void main(String[] args) {
        try {
            // Creamos un socket UDP para el cliente
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
            // Scanner para leer la entrada del usuario
            Scanner scanner = new Scanner(System.in);

            // Bucle para permitir múltiples operaciones
            while(true) {
                System.out.println("Operación (SUMA o RESTA o EXIT para salir); ");
                String operation = scanner.nextLine().toUpperCase();

                // Condición para salir del programa
                if(operation.equals("EXIT")) {
                    System.out.println("Saliendo del programa.");
                    break;
                }

                // Pedimos los números al usuario
                System.out.print("Primer número: ");
                int num1 = Integer.parseInt(scanner.nextLine());
                System.out.print("Segundo número: ");
                int num2 = Integer.parseInt(scanner.nextLine());

                // Formateamos el mensaje a enviar al servidor: OPERACION:num1:num2
                String message = operation + ":" + num1 + ":" + num2;
                byte[] sendData = message.getBytes();

                // Creamos el paquete UDP con el mensaje y la dirección del servidor
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
                clientSocket.send(sendPacket);

                // Preparamos un buffer para recibir la respuesta del servidor
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                // Esperamos la respuesta del servidor (bloqueante)
                clientSocket.receive(receivePacket); // Enviamos el paquete

                // Convertimos la respuesta en String y la mostramos
                String result = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Respuesta del servidor: " + result);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar errores en caso de fallo
        }
    }
}

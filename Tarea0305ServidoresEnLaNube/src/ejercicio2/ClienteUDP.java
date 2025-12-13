package ejercicio2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            // Importante: ahora funciona con 'localhost', pero luego pondremos la IP de la EC2
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            int puertoServidor = 5001;

            Scanner scanner = new Scanner(System.in);
            System.out.println("Cliente UDP conectado. Escribe un número para ver sus primos.");

            while (true) {
                System.out.print("Número (o 'salir'): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("salir")) break;

                byte[] datosEnviar = input.getBytes();
                DatagramPacket paqueteEnvio = new DatagramPacket(
                        datosEnviar,
                        datosEnviar.length,
                        direccionServidor,
                        puertoServidor
                );
                socket.send(paqueteEnvio);

                byte[] bufferRecepcion = new byte[4096];
                DatagramPacket paqueteRecepcion = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);

                socket.receive(paqueteRecepcion);

                String respuesta = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
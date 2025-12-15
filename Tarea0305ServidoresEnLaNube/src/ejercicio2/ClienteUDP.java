package ejercicio2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class ClienteUDP {
    public static void main(String[] args) {
        try {
            // 1. Creación del Socket
            // Dejamos el constructor vacío. El Sistema Operativo asignará automáticamente
            // un puerto libre aleatorio a este cliente para poder enviar y recibir.
            DatagramSocket socket = new DatagramSocket();

            // 2. Configuración del Destino
            // Funcionamiento con 'localhost'
               // InetAddress direccionServidor = InetAddress.getByName("localhost");
            // Funcionamiento con la instancia de AWS
            InetAddress direccionServidor = InetAddress.getByName("35.172.164.6");
            int puertoServidor = 5001; // Debe coincidir con el puerto que abrió el servidor

            Scanner scanner = new Scanner(System.in);
            System.out.println("Cliente UDP conectado. Escribe un número para ver sus primos.");

            while (true) {
                System.out.print("Número (o 'salir'): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("salir")) break;

                // 3. Preparar el Envío (Request)
                // UDP transmite bytes, no texto. Convertimos el String a array de bytes.
                byte[] datosEnviar = input.getBytes();

                // Creamos el "sobre" (DatagramPacket) que contiene:
                // - Los datos (la carta)
                // - La longitud de los datos
                // - La dirección y el puerto de destino
                DatagramPacket paqueteEnvio = new DatagramPacket(
                        datosEnviar,
                        datosEnviar.length,
                        direccionServidor,
                        puertoServidor
                );

                // Enviamos el paquete
                socket.send(paqueteEnvio);

                // 4. Preparar la Recepción
                // Creamos un buffer vacío lo suficientemente grande (4KB) para alojar la respuesta,
                // ya que la lista de números primos puede ser larga.
                byte[] bufferRecepcion = new byte[4096];
                DatagramPacket paqueteRecepcion = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);

                // Esperamos la respuesta.
                // El programa se detiene (bloquea) en esta línea hasta que llega un paquete.
                socket.receive(paqueteRecepcion);

                // 5. Procesar la Respuesta
                // Reconstruimos el String desde los bytes recibidos.
                // Usamos 'paqueteRecepcion.getLength()' para leer solo los datos útiles,
                // ignorando el espacio vacío sobrante del buffer.
                String respuesta = new String(paqueteRecepcion.getData(), 0, paqueteRecepcion.getLength());
                System.out.println("Respuesta del servidor: " + respuesta);
            }

            // Cerrar el socket para liberar el puerto del sistema
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
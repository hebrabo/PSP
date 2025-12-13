package ejercicio1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteJuego {
    public static void main(String[] args) {
        String host = "localhost"; // Cambiaremos esto por la IP de AWS luego
        int puerto = 5000;

        try (Socket socket = new Socket(host, puerto)) {
            System.out.println("Conectado al servidor de juego.");

            // Canales de comunicación
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            boolean jugar = true;

            // Bucle principal del juego (para permitir revanchas)
            while (jugar) {
                // 1. Recibir mensaje de bienvenida del servidor
                String bienvenida = entrada.readLine();
                System.out.println("Servidor: " + bienvenida);

                boolean acertado = false;

                // Bucle de intentos
                while (!acertado) {
                    System.out.print("Introduce un número: ");
                    String intento = scanner.nextLine();

                    // Enviar intento al servidor
                    salida.println(intento);

                    // Recibir respuesta (Mayor, Menor o Correcto)
                    String respuesta = entrada.readLine();
                    System.out.println("Servidor: " + respuesta);

                    // Usamos toLowerCase() para evitar problemas de mayúsculas
                    // y comprobamos si es nulo por seguridad.
                    if (respuesta != null && respuesta.toLowerCase().contains("correcto")) {
                        acertado = true;
                    }
                }

                // 2. Lógica de "Jugar otra vez"
                // El servidor envía la pregunta "¿Quieres jugar otra vez?" justo después de acertar
                String pregunta = entrada.readLine();
                System.out.println("Servidor: " + pregunta);

                String decision = scanner.nextLine();
                salida.println(decision); // Enviamos 's' o 'n'

                if (!decision.equalsIgnoreCase("s")) {
                    jugar = false;
                    // Leer mensaje de despedida
                    String despedida = entrada.readLine();
                    System.out.println("Servidor: " + despedida);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
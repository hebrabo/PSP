package ejercicio1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteJuego {
    public static void main(String[] args) {
        // En local usamos "localhost". En AWS usaremos la IP pública de la instancia.
        String host = "localhost";
        int puerto = 5000;

        // Try-with-resources: Cierra el socket automáticamente al terminar el bloque.
        try (Socket socket = new Socket(host, puerto)) {
            // Aquí se conecta con el servidor creando un flujo de datos continuo
            // que garantiza que los mensajes lleguen en orden y sin errores.
            System.out.println("Conectado al servidor de juego.");

            // Configuración de Streams (Flujos de datos):
            // 1. Salida (Hacia el servidor): PrintWriter permite enviar texto fácilmente.
            //    IMPORTANTE: El 'true' activa el 'autoFlush'. Si no se pone, el mensaje podría
            //    quedarse atascado en el buffer de memoria y no enviarse nunca.
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // 2. Entrada (Desde el servidor): BufferedReader permite leer línea a línea.
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 3. Entrada de usuario (Teclado)
            Scanner scanner = new Scanner(System.in);

            boolean jugar = true;

            // Bucle principal (Controla si jugamos una nueva partida o salimos)
            while (jugar) {
                // EL PROTOCOLO ES CLAVE EN TCP:
                // Sabemos que lo primero que hace el servidor es enviarnos un saludo.
                // Por eso, lo primero que hacemos es leer (readLine es BLOQUEANTE).
                String bienvenida = entrada.readLine();
                System.out.println("Servidor: " + bienvenida);

                boolean acertado = false;

                // Bucle de intentos (Lógica del juego)
                while (!acertado) {
                    System.out.print("Introduce un número: ");
                    String intento = scanner.nextLine();

                    // Enviamos el dato al servidor a través del socket
                    salida.println(intento);

                    // Esperamos la respuesta (Mayor, Menor o Correcto)
                    // El programa se "congela" aquí hasta que llega la respuesta del servidor.
                    String respuesta = entrada.readLine();
                    System.out.println("Servidor: " + respuesta);

                    // Verificamos si hemos ganado
                    // Usamos validaciones robustas (null check y ignore case)
                    if (respuesta != null && respuesta.toLowerCase().contains("correcto")) {
                        acertado = true;
                        // Al salir de este bucle, seguimos el flujo secuencial
                    }
                }

                // 2. Lógica de "Revancha"
                // Según nuestro protocolo, justo después de acertar, el servidor envía una pregunta.
                // Debemos leerla antes de poder decidir.
                String pregunta = entrada.readLine();
                System.out.println("Servidor: " + pregunta);

                String decision = scanner.nextLine();
                salida.println(decision); // Le decimos al servidor 's' o 'n'

                if (!decision.equalsIgnoreCase("s")) {
                    jugar = false; // Rompemos el bucle principal

                    // Leemos el mensaje final de desconexión que envía el servidor
                    // justo antes de terminar la ejecución.
                    String despedida = entrada.readLine();
                    System.out.println("Servidor: " + despedida);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Aquí el socket se cierra automáticamente (socket.close()) gracias al try-with-resources
    }
}
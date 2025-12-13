package ejercicio1;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ServidorJuego {
    public static void main(String[] args) {
        int puerto = 5000;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor de Juego (Adivina Número) escuchando en el puerto " + puerto);

            while (true) {
                // Esperar conexión
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo jugador conectado: " + clientSocket.getInetAddress());

                // Lanzar hilo para el jugador
                Thread jugadorThread = new Thread(new JugadorHandler(clientSocket));
                jugadorThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class JugadorHandler implements Runnable {
    private Socket socket;
    private Random random;

    public JugadorHandler(Socket socket) {
        this.socket = socket;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            // Canales de comunicación
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            boolean jugando = true;

            // Bucle principal de reinicio del juego
            while (jugando) {
                int numeroSecreto = random.nextInt(100) + 1; // 1 a 100
                System.out.println("Juego iniciado para " + socket.getInetAddress() + ". Número secreto: " + numeroSecreto);

                salida.println("¡Bienvenido! He pensado un número entre 1 y 100. ¡Adivina!");

                boolean adivinado = false;

                // Bucle de intentos
                while (!adivinado) {
                    String mensajeCliente = entrada.readLine();

                    if (mensajeCliente == null) {
                        jugando = false;
                        break; // Cliente se desconectó
                    }

                    try {
                        int intento = Integer.parseInt(mensajeCliente);

                        if (intento < numeroSecreto) {
                            salida.println("El número es mayor");
                        } else if (intento > numeroSecreto) {
                            salida.println("El número es menor");
                        } else {
                            salida.println("¡¡¡NÚMERO CORRECTO!!"); // Mensaje clave para el cliente
                            adivinado = true;
                        }
                    } catch (NumberFormatException e) {
                        salida.println("Por favor, introduce un número válido.");
                    }
                }

                if (jugando) {
                    // Preguntar si quiere jugar otra vez
                    salida.println("¿Quieres jugar otra vez? (s/n)");
                    String respuesta = entrada.readLine();
                    if (respuesta == null || !respuesta.equalsIgnoreCase("s")) {
                        jugando = false;
                        salida.println("¡Gracias por jugar! Adiós.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Jugador desconectado abruptamente.");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package ejercicio1;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ServidorJuego {
    public static void main(String[] args) {
        int puerto = 5000;

        // Utilizamos un try-with-resources para asegurar que el ServerSocket se cierre si hay error de arranque.
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor de Juego (Adivina Número) escuchando en el puerto " + puerto);

            // Bucle principal del servidor.
            // Su única función es estar siempre alerta para aceptar nuevas conexiones.
            while (true) {
                // 1. Esperar conexión (BLOQUEANTE)
                // El hilo principal se detiene en esta línea hasta que un cliente intenta conectarse.
                // Cuando ocurre, retorna un objeto 'Socket' exclusivo para ese cliente.
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo jugador conectado: " + clientSocket.getInetAddress());

                // 2. Gestión de la Concurrencia (Hilos)
                // En lugar de atender el juego aquí (lo que bloquearía a otros clientes),
                // creamos un nuevo hilo (Thread) y le pasamos la tarea.
                // El hilo principal vuelve inmediatamente al principio del 'while' para aceptar a otro.
                Thread jugadorThread = new Thread(new JugadorHandler(clientSocket));
                jugadorThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Clase Handler: Contiene la lógica del juego para UN solo cliente.
// Implementa Runnable para poder ejecutarse en un hilo paralelo.
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
            // Configuración de canales de comunicación (Streams)
            // BufferedReader: Para leer texto línea a línea del cliente.
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // PrintWriter: Para enviar texto. 'true' activa el autoFlush (envío inmediato).
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            boolean jugando = true;

            // Bucle de Sesión: Permite jugar varias partidas sin desconectarse.
            while (jugando) {
                int numeroSecreto = random.nextInt(100) + 1; // Genera entre 1 y 100
                System.out.println("Juego iniciado para " + socket.getInetAddress() + ". Número secreto: " + numeroSecreto);

                // Protocolo: El servidor toma la iniciativa enviando el mensaje de bienvenida.
                salida.println("¡Bienvenido! He pensado un número entre 1 y 100. ¡Adivina!");

                boolean adivinado = false;

                // Bucle de Partida: Se repite hasta que el usuario acierte.
                while (!adivinado) {
                    // Lectura bloqueante: Esperamos a que el cliente envíe su intento.
                    String mensajeCliente = entrada.readLine();

                    // Control de desconexión abrupta:
                    // Si el cliente cierra la ventana o pierde conexión, readLine() devuelve null.
                    if (mensajeCliente == null) {
                        jugando = false;
                        break; // Salimos del bucle interno
                    }

                    try {
                        int intento = Integer.parseInt(mensajeCliente);

                        // Lógica de comparación
                        if (intento < numeroSecreto) {
                            salida.println("El número es mayor");
                        } else if (intento > numeroSecreto) {
                            salida.println("El número es menor");
                        } else {
                            // Protocolo: Esta cadena específica avisa al cliente de que ha ganado.
                            salida.println("¡¡¡NÚMERO CORRECTO!!");
                            adivinado = true;
                        }
                    } catch (NumberFormatException e) {
                        salida.println("Por favor, introduce un número válido.");
                    }
                }

                // Si el jugador sigue conectado y ha terminado la partida anterior...
                if (jugando) {
                    salida.println("¿Quieres jugar otra vez? (s/n)");

                    // Esperamos la decisión del usuario
                    String respuesta = entrada.readLine();

                    // Verificación robusta: Si es null (se fue) o no es 's', terminamos.
                    if (respuesta == null || !respuesta.equalsIgnoreCase("s")) {
                        jugando = false;
                        salida.println("¡Gracias por jugar! Adiós.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Jugador desconectado abruptamente.");
        } finally {
            // BLOQUE FINALLY:
            // Pase lo que pase (error o fin normal), cerramos el socket para liberar
            // los recursos del sistema operativo (puertos y memoria).
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
/*
 * Cada cliente conectado tiene un hilo independiente (HiloServidor) que escucha sus mensajes.
 * Los mensajes se leen con fentrada.readUTF().
 * Cuando un cliente envía "*", se interpreta como desconexión y se actualiza el número de clientes.
 * Cada mensaje recibido se agrega al historial (textarea) y se reenvía a todos los clientes usando EnviarMensajes().
 * El metodo EnviarMensajes recorre todos los sockets de clientes y envía el texto completo para mantenerlos sincronizados.
 */

package servidor;

import java.io.*;
import java.net.*;

public class HiloServidor extends Thread {
	DataInputStream fentrada; // flujo de entrada para recibir mensajes del cliente
	Socket socket = null; // socket asociado a un cliente concreto

    // Constructor: recibe el socket del cliente y prepara el flujo de entrada
    public HiloServidor(Socket s) {
		socket = s;
		try {
            // Creamos el flujo de entrada para leer mensajes del cliente
			fentrada = new DataInputStream(socket.getInputStream());			
		} catch (IOException e) {
			System.out.println("ERROR DE E/S");
			e.printStackTrace();
		}
	}// ..

    // Metodo principal del hilo: se ejecuta en paralelo para cada cliente
    public void run() {
        // Actualizamos la interfaz del servidor con el número de conexiones actuales
        ServidorChat.mensaje.setText("NUMERO DE CONEXIONES ACTUALES: "
				+ ServidorChat.ACTUALES);

        // Cuando un cliente se conecta, le enviamos todo el historial de mensajes
		String texto = ServidorChat.textarea.getText();
		EnviarMensajes(texto);

        // Bucle infinito para recibir mensajes continuamente
        while (true) {
			String cadena = "";
			try {
				cadena = fentrada.readUTF(); // leemos mensaje del cliente
				if (cadena.trim().equals("*")) {// si el mensaje es "*", el cliente se desconecta
					ServidorChat.ACTUALES--; // actualizamos el contador de clientes
					ServidorChat.mensaje
							.setText("NUMERO DE CONEXIONES ACTUALES: "
									+ ServidorChat.ACTUALES);// Datos.getConexiones());
					break; // salimos del bucle y terminamos el hilo
				}

                // Añadimos el mensaje recibido al historial del servidor
                ServidorChat.textarea.append(cadena + "\n");

                // Obtenemos el historial completo y lo enviamos a todos los clientes
                texto = ServidorChat.textarea.getText();
				EnviarMensajes(texto);

			} catch (Exception e) {
				e.printStackTrace(); // en caso de error, imprimimos y salimos
				break;
			}
		}// fin while
	}//run

    // Metodo que envía el historial de mensajes a todos los clientes conectados
	private void EnviarMensajes(String texto) {
		int i;
		//recorremos tabla de sockets de todos los clientes para enviarles los mensajes
		for (i = 0; i < ServidorChat.CONEXIONES; i++) {
			Socket s1;
			s1 = ServidorChat.tabla[i];			
			try {
                // Creamos un flujo de salida para cada cliente y enviamos el mensaje
                DataOutputStream fsalida2 = new DataOutputStream(
						s1.getOutputStream());
				fsalida2.writeUTF(texto);				
			} catch (SocketException se) {//hay que dejarlo
				// puede ocurrir cuando finalizo cliente
				// sale cuando un cliente ha finalizado
				System.out.println(" SOCKET EXCEPTION 2 : " + se.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				//System.out.println(" IO EXCEPTION  : " + e.getMessage());
			}			
		}//for
	}//EnviarMensajes
}// ..HiloServidor
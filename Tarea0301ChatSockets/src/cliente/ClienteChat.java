
/* Cada cliente crea un socket TCP para conectarse al servidor y flujos de entrada/salida para enviar y recibir mensajes.
 * Los mensajes escritos por el usuario se envían al servidor usando fsalida.writeUTF() al pulsar el botón “Enviar”.
 * El cliente mantiene un bucle (ejecutar()) que lee continuamente los mensajes enviados por el servidor usando fentrada.readUTF().
 * Cuando el usuario pulsa “Salir”, el cliente envía un mensaje especial "*" al servidor para indicar desconexión y termina el bucle de lectura.
 * Cada mensaje recibido se muestra en el área de texto (textarea1) para que el usuario vea todo el historial del chat.
*/
package cliente;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ClienteChat extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

    Socket socket = null; // Socket para conectarse al servidor

    // Streams para enviar y recibir mensajes
	DataInputStream fentrada; // flujo de entrada desde el servidor
	DataOutputStream fsalida; // flujo de salida hacia el servidor

    String nombre; // nombre o nick del cliente
	static JTextField mensaje = new JTextField(); // campo donde el usuario escribe mensajes

	private JScrollPane scrollpane1;
	static JTextArea textarea1; // área donde se muestran los mensajes del chat
	JButton boton = new JButton("Enviar"); // botón para enviar mensajes
	JButton desconectar = new JButton("Salir"); // botón para desconectarse
	boolean repetir = true; // controla el bucle de lectura de mensajes

	// constructor
	public ClienteChat(Socket s, String nombre) {
		super(" CONEXIÓN DEL CLIENTE CHAT: " + nombre);
		setLayout(null);

        // Configuración del campo de texto para enviar mensajes
        mensaje.setBounds(10, 10, 400, 30);
		add(mensaje);

        // Área de texto donde se mostrarán los mensajes
        textarea1 = new JTextArea();
		scrollpane1 = new JScrollPane(textarea1);
		scrollpane1.setBounds(10, 50, 400, 300);
		add(scrollpane1);

        // Botones para enviar mensaje y desconectar
        boton.setBounds(420, 10, 100, 30);
		add(boton);
		desconectar.setBounds(420, 50, 100, 30);
		add(desconectar);

        // No se puede escribir en el área de mensajes
        textarea1.setEditable(false);

        // Asociar acciones a los botones
        boton.addActionListener(this);
		desconectar.addActionListener(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // no cerrar con la cruz

		socket = s;
		this.nombre = nombre;
		try {
            fentrada = new DataInputStream(socket.getInputStream()); // Crear flujo de entrada al socket
			fsalida = new DataOutputStream(socket.getOutputStream()); // CREO FLUJO DE SALIDA AL socket de escritura

            // Mensaje inicial indicando que el usuario ha entrado al chat
            String texto = " > Entra en el Chat ... " + nombre;
			fsalida.writeUTF(texto);
		} catch (IOException e) {
			System.out.println("ERROR DE E/S");
			e.printStackTrace();
			System.exit(0);
		}
	}// fin constructor

	// Acción cuando pulsamos botones
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == boton) { // Si se pulsa "Enviar"
			String texto = nombre + "> " + mensaje.getText();
			try {
				mensaje.setText(""); // limpiar campo de entrada
				fsalida.writeUTF(texto); // enviar mensaje al servidor
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == desconectar) { // Si se pulsa "Salir/Desconectar"
			String texto = " > Abandona el Chat ... " + nombre;
			try {
				fsalida.writeUTF(texto); // enviar aviso al chat
				fsalida.writeUTF("*"); // mensaje especial para indicar desconexión
				repetir = false; // parar el bucle de lectura de mensajes
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
		}
	}

    // Bucle que recibe mensajes del servidor y los muestra en la interfaz
	public void ejecutar() {
		String texto = "";
		while (repetir) {
			try {
				texto = fentrada.readUTF();			
				textarea1.setText(texto);			
				
			} catch (IOException e) {
				// este error sale cuando el servidor se cierra
				JOptionPane
						.showMessageDialog(
								null,
								"IMPOSIBLE CONECTAR CON EL SERVIDOR\n"
										+ e.getMessage(),
								"<<MENSAJE DE ERROR:2>>",
								JOptionPane.ERROR_MESSAGE);
				// System.exit(0);
				repetir = false;
			}
		}//while

        // Cerrar socket al salir
        try {
			socket.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// ejecutar

    // Metodo principal
    public static void main(String args[]) {
		int puerto = 44444;
		String nombre = JOptionPane.showInputDialog("Introduce tu nombre o nick:");
		// Socket s = new Socket("localhost", Servidor.PUERTO);
		// Creación del socket, y la conexión a la máquina remota con dirección
		// IP "192.168.0.193"
		//
		Socket s = null;
		try {
			s = new Socket("localhost", puerto); // conectarse al servidor
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"IMPOSIBLE CONECTAR CON EL SERVIDOR\n" + e.getMessage(),
					"<<MENSAJE DE ERROR:1>>", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		if (!nombre.trim().equals("")) {
			ClienteChat cliente = new ClienteChat(s, nombre);
			cliente.setBounds(0, 0, 540, 400);
			cliente.setVisible(true);
			cliente.ejecutar(); // iniciar bucle de lectura de mensajes
		} else {
			System.out.println("El nombre está vacío....");
		}
	}//main
}// ..ClienteChat
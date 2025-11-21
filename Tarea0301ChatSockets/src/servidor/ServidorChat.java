
/* Se configura la interfaz gráfica del servidor: número de clientes, historial de mensajes y botón de salir.
 * Se inicializan los contadores de conexiones (CONEXIONES y ACTUALES) y el máximo permitido.
 * Se crea la tabla de sockets (tabla) para almacenar las conexiones de los clientes.
 * Se prepara el campo de texto y el área de mensajes para mostrar información al administrador del chat.
*/
package servidor;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServidorChat extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

    // Servidor TCP que escucha conexiones de clientes
    static ServerSocket servidor;

    static final int PUERTO = 44444;// puerto por el que el servidor escucha
	static int CONEXIONES = 0; // contador total de conexiones realizadas
	static int ACTUALES = 0; // número de clientes actualmente conectados
	static int MAXIMO=4;//MAXIMO DE CONEXIONES PERMITIDAS

    // Componentes de la interfaz gráfica del servidor
    static JTextField mensaje=new JTextField(""); // muestra número de conexiones actuales
	static JTextField mensaje2=new JTextField(""); // muestra mensaje cuando se alcanza el máximo
	private JScrollPane scrollpane1;
	static JTextArea textarea; // área donde se muestra el historial de mensajes
	JButton salir = new JButton("Salir"); // botón para cerrar el servidor

    // Tabla de sockets para controlar los clientes conectados
    static Socket tabla[] = new Socket[10];// máximo 10 clientes en la tabla

    // Constructor de la ventana del servidor
	public ServidorChat() {
		super(" VENTANA DEL SERVIDOR DE CHAT "); // título de la ventana
		setLayout(null);

        // Campo que muestra el número de conexiones actuales
        mensaje.setBounds(10, 10, 400, 30);
		add(mensaje);
        mensaje.setEditable(false); // solo lectura

        // Campo que muestra advertencia cuando se alcanza el máximo de conexiones
        mensaje2.setBounds(10, 348, 400, 30);
        add(mensaje2);
		mensaje2.setEditable(false); // solo lectura

        // Área de texto donde se mostrará el historial de mensajes
        textarea = new JTextArea();
		scrollpane1 = new JScrollPane(textarea);
		scrollpane1.setBounds(10, 50, 400, 300);
        add(scrollpane1);

        // Botón para cerrar el servidor
		salir.setBounds(420, 10, 100, 30);
        add(salir);
		textarea.setEditable(false);
		salir.addActionListener(this); // asociamos la acción del botón
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // no cerrar con la cruz

	}

    // Metodo llamado cuando se pulsa el botón "Salir"
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == salir) {
			try {
				servidor.close(); // cerramos el socket servidor
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0); // terminamos el programa
		}
	} //

	public static void main(String args[]) throws IOException {
        // Se inicia el servidor escuchando en el puerto indicado
        servidor = new ServerSocket(PUERTO);
		System.out.println("Servidor iniciado...");

        // Se crea la ventana del servidor
        ServidorChat pantalla = new ServidorChat();
		pantalla.setBounds(0, 0, 540, 400);
		pantalla.setVisible(true);

        // Muestra que hay 0 clientes conectados al iniciar
        mensaje.setText("NUMERO DE CONEXIONES ACTUALES: " + 0);

        // Bucle que acepta conexiones mientras no se alcance el máximo permitido
		while (CONEXIONES < MAXIMO) {
			Socket s = new Socket();
			try {
				s = servidor.accept();// el servidor se queda esperando un cliente
			} catch (SocketException ns) {
                // Si pulsamos salir, el servidor se cierra y accept() lanza excepción
				break;
			}

            // Guardamos el socket del cliente en la tabla
			tabla[CONEXIONES] = s;

            // Actualizamos contadores
            CONEXIONES++;
			ACTUALES++;

            // Cada cliente tendrá un hilo independiente que lo atiende
            HiloServidor hilo = new HiloServidor(s);
			hilo.start();
		}
        // Fuera del bucle: se cerrará el servidor porque alcanzó el máximo de clientes
		if (!servidor.isClosed())
			try {
				// sale cuando se llega al máximo de conexiones
				mensaje2.setForeground(Color.red);
				mensaje2.setText("MÁXIMO Nº DE CONEXIONES ESTABLECIDAS: " + CONEXIONES);
				servidor.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		System.out.println("Servidor finalizado..."); 	
	}
}// ..


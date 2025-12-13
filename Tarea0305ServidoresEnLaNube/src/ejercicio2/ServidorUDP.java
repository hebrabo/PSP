package ejercicio2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ServidorUDP {
    public static void main(String[] args) {
        int puerto = 5001;

        try {
            DatagramSocket socket = new DatagramSocket(puerto);
            System.out.println("Servidor de Primos (UDP) esperando en puerto " + puerto + "...");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                socket.receive(paqueteRecibido);

                String mensaje = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
                InetAddress direccionCliente = paqueteRecibido.getAddress();
                int puertoCliente = paqueteRecibido.getPort();

                System.out.println("Solicitud recibida de: " + direccionCliente + ":" + puertoCliente);

                // Lanzamos el hilo
                HiloPrimos tarea = new HiloPrimos(socket, direccionCliente, puertoCliente, mensaje);
                new Thread(tarea).start();

                buffer = new byte[1024];
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class HiloPrimos implements Runnable {
    private DatagramSocket socket;
    private InetAddress direccion;
    private int puerto;
    private String mensaje;

    public HiloPrimos(DatagramSocket socket, InetAddress direccion, int puerto, String mensaje) {
        this.socket = socket;
        this.direccion = direccion;
        this.puerto = puerto;
        this.mensaje = mensaje;
    }

    private boolean esPrimo(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public void run() {
        try {
            int numero = Integer.parseInt(mensaje.trim());
            List<String> listaPrimos = new ArrayList<>();

            for (int i = 2; i <= numero; i++) {
                if (esPrimo(i)) {
                    listaPrimos.add(String.valueOf(i));
                }
            }

            String respuesta = String.join(",", listaPrimos);
            byte[] datosEnviar = respuesta.getBytes();

            DatagramPacket paqueteEnvio = new DatagramPacket(
                    datosEnviar,
                    datosEnviar.length,
                    direccion,
                    puerto
            );

            synchronized (socket) {
                socket.send(paqueteEnvio);
            }
            System.out.println("Primos enviados a " + direccion + ":" + puerto);

        } catch (NumberFormatException e) {
            System.out.println("Error: El cliente no envió un número válido.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

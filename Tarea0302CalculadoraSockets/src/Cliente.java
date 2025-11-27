import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            // Establecer conexión con el servidor.
            // "localhost" significa que el servidor está en la misma máquina.
            // 12345 es el puerto donde el servidor está escuchando.
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Conexion establecida con el servidor.");

            boolean end = false;
            String result;

            while(!end) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Introduce tipo de operación: 1 - Suma, 2 - resta, 3 - multiplicación, 4 - División");
                String tipoOperacion = scanner.next();

                System.out.println("Introduce primer valor");
                String primerValor = scanner.next();

                System.out.println("Introduce el segundo valor");
                String segundoValor = scanner.next();

                String message = tipoOperacion + "#" + primerValor + "#" + segundoValor;

                sendMessage(socket, message);
                result = receiveMessage(socket);

                System.out.println("El resultado es: " + result);

                System.out.println("Si quieres hacer otra operación inserta cualquier valor numérico que no sea 0, si quieres salir inserta 0.");
                int option = scanner.nextInt();

                if(option == 0) {
                    end = true;
                    sendMessage(socket, "close");
                }
            }

            // Cerrar el socket después de la comunicación
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String receiveMessage(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        return br.readLine();
    }

    private static void sendMessage(Socket socket, String textoEnviar) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
        bw.write(textoEnviar);
        bw.newLine();
        bw.flush();

    }
}

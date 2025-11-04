import java.io.*;

public class HijoPipe {
    public static void main(String[] args) {
        try {
            // Leer lo que el padre envía
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String mensaje = reader.readLine();

            // Responder al padre
            System.out.println("Hijo recibe: " + mensaje);
            System.out.println("Hola padre, soy el hijo y recibí tu mensaje.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
import java.io.*;

public class Hijo {
    public static void main(String[] args) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))
                ) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.equalsIgnoreCase("fin")) {
                    break;
                }
                // Convertir a mayúsculas y enviar al padre
                writer.write(linea.toUpperCase() + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Error en el proceso hijo: " + e.getMessage());
        }
    }
}

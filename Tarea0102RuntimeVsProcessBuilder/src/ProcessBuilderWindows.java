import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessBuilderWindows {
    public static void main(String[] args) {
        try {
            // 1. Crear una instancia de ProcessBuilder (para ejecutar un comando del sistema operativo)
            // En Windows: cmd /c dir → abre la consola, ejecuta 'dir' y se cierra
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "dir");

            // 2. Iniciar el proceso
            Process process = processBuilder.start();

            // 3. Capturar la salida del proceso (lo que el comando imprime en pantalla)
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            System.out.println("=== Listado de archivos en el directorio actual ===");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 4. Esperar a que el proceso termine
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Comando ejecutado correctamente.");
            } else {
                System.out.println("Error en la ejecución del comando. Código: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            // Manejo de errores (según las buenas prácticas, se podría usar un logger)
            e.printStackTrace();
        }
    }
}


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * PSP Tarea0102: Ejecución de Comandos en Java utilizando Runtime
 * Este programa ejecuta un comando del sistema operativo Windows
 * para listar los archivos del directorio actual utilizando la clase Runtime.
 * PASOS SEGÚN EL TEMARIO:
 * 1. Obtener una instancia de Runtime
 * 2. Ejecutar un comando del sistema
 * 3. Capturar la salida del proceso
 * 4. Esperar a que el proceso termine
 */

public class RuntimeWindows {
    public static void main(String[] args) {
        try{
            // 1. Obtener una instancia de Runtime (Runtime es una clase de Java diseñada para interactuar con el sistema operativo.)
            Runtime runtime = Runtime.getRuntime(); // Le estás diciendo a Java: “Dame el entorno de ejecución actual, porque quiero interactuar con el sistema operativo”.

            // 2. Ejecutar un comando del sistema operativo desde Java.
            // “Abre la consola de Windows (cmd), ejecuta lo que viene después y luego cierra (/c), lista los archivos y carpetas del directorio actual (dir)”.
            // El metodo exec() lanza un nuevo proceso del sistema (proceso hijo).
            // La variable 'proceso' representa ese proceso en ejecución.
            Process proceso = runtime.exec("cmd /c dir");

            // 3. Capturar la salida del proceso (mostrar, en formato texto, lo que el proceso ha hecho o producido.).
            // getInputStream() obtiene la salida del proceso.
            // InputStreamReader convierte los bytes en texto legible.
            // BufferedReader permite leer ese texto línea por línea.
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream())
            );
            String linea;
            while ((linea = reader.readLine()) != null){
                System.out.println(linea);
            }

            //4. Esperar a que el proceso termine
            // waitFor() le dice a Java: “Espera hasta que el proceso hijo (el comando que ejecutaste) termine.”
            // Esto bloquea la ejecución del programa hasta que el proceso haya finalizado.
            int resultado = proceso.waitFor();
            if (resultado == 0) {
                System.out.println("Comando ejecutado correctamente");
            } else {
                System.out.println("Error en la ejecucion del comando");
            }

        } catch (IOException | InterruptedException e){
            // Manejo de errores (según las buenas prácticas, se podría usar un logger)
            e.printStackTrace();
        }
    }
}



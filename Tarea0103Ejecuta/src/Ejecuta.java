import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*Escribe una clase llamada Ejecuta que reciba como argumentos el comando y las opciones del comando que se quiere ejecutar.
* El programa debe crear un proceso hijo que ejecute el comando con las opciones  correspondientes
* mostrando un mensaje de error en el caso de no  ejecutarse correctamente.
* El padre debe esperar a que el hijo termine de informar si se produjo alguna anomalía en la ejecución del hijo.
 */
public class Ejecuta {
    public static void main(String[] args){
        // Validar que al menos se haya pasado un comando
        if (args.length < 1){
            System.err.println("Error: Debe proporcionar un comando para ejecutar.");
            System.exit(1);
        }

        // Separar el comando y sus argumentos (Estos comentarios muestran cómo podríamos separar manualmente el comando principal (args[0]) de sus argumentos (args[1..n]).
        // Nosotros optamos por pasar todo directamente a ProcessBuilder con builder.command(args).
        // String[] comando = new String[]{args[0]};
        // String[] opciones = new String[args.length - 1];
        // System.arraycopy(args, 1, opciones, 0, args.length -1);

        // Construir el proceso con ProcessBuilder
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(args);
        builder.redirectErrorStream(true); // Combinar la salida stándar y de error

        try{
            // Iniciar el proceso hijo
            Process proceso = builder.start();

            // Leer la salida del proceso hijo
            BufferedReader salida = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = salida.readLine()) != null){
                System.out.println(linea);
            }

            // Esperar a que el proceso termine
            int exitCode = proceso.waitFor();

            // Informar sobre el resultado del proceso
            if (exitCode == 0) {
                System.out.println("El comando se ejecutó correctamente");
            } else {
                System.out.println("El comando terminó con errores. Código de salida: " + exitCode);
            }

        } catch (IOException e) {
            System.err.println("Error al intentar ejecutar el comando: " + e.getMessage());
        } catch (InterruptedException e){
            System.err.println("La ejecución del proceso fue interrumpida: " + e.getMessage());
            //Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
        }
    }
}

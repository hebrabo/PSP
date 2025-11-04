/* Escribe una clase llamada Mayusculas que haga lo siguiente:
1) Cree un proceso hijo.
2) El proceso padre y el proceso hijo se comunicarán de forma bidireccional utilizando Streams.
3) El proceso padre leerá líneas de su entrada estándar y las enviará a la entrada estándar del hijo (utilizando OutputStream del hijo).
3) El proceso hijo leerá el texto por su entrada estándar, lo transformará todo a letras mayúsculas y lo imprimirá todo por su salida estándar.
4) El proceso padre imprimirá por pantalla (salida estándar) lo que recibe del hijo a través del InputStream del mismo.

- El programa finalizará cuando el usuario escriba la palabra "fin".
- El proceso padre debe esperar a que termine el proceso hijo (waitFor()).
- Usa System.err.println(); para los mensajes de error.
- Investiga sobre PrintWriter y BufferedWriter antes de empezar.
 */

import java.io.*;
import java.util.Scanner;

public class Mayusculas {
    public static void main(String[] args) {
        try{
            // Crear el proceso hijo
            ProcessBuilder processBuilder = new ProcessBuilder("java", "Hijo");
            processBuilder.redirectErrorStream(true);
            Process processHijo = processBuilder.start();

            // Streams para la comunicación
            OutputStream os = processHijo.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

            InputStream is = processHijo.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            // Leer líneas desde la entrada standard (padre)
            Scanner scanner = new Scanner(System.in);
            System.out.println("Escribe texto para convertir a mayúsculas. Escribe 'fin' para terminar:");

            String linea;
            while(scanner.hasNextLine()){
                linea = scanner.nextLine();

                // Enviar línea al hijo
                writer.write(linea + "\n");
                writer.flush();

                if (linea.equalsIgnoreCase("fin")){
                    break;
                }

                // Leer respuesta del hijo
                String respuesta = reader.readLine();
                System.out.println("Desde el hijo: " + respuesta);
            }

            // Cerrar los streams y esperar a que el proceso hijo termine
            writer.close();
            reader.close();
            processHijo.waitFor();

        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

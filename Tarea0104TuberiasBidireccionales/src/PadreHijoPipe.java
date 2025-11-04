import java.io.*;

public class PadreHijoPipe {
    public static void main(String[] args) {
        try {
            // Comando para lanzar el proceso processHijo
            ProcessBuilder processBuilder = new ProcessBuilder("java", "HijoPipe");
            processBuilder.redirectErrorStream(true); // Hace que los errores del hijo también se puedan leer por el mismo canal de salida.

            // Iniciar el proceso processHijo
            Process processHijo = processBuilder.start();

            // Configurar Salida del padre -> Entrada del processHijo
            // getOutputStream() nos da un canal para enviar datos al hijo.
            // PrintWriter se usa para escribir en ese canal de manera fácil.
            // true habilita el auto-flush, asegurando que los mensajes se envían inmediatamente.
            OutputStream os = processHijo.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            // Configurar Entrada del processHijo -> Salida del padre
            // getInputStream() nos da un canal para leer la salida del hijo.
            // BufferedReader permite leer la salida línea por línea.
            InputStream is = processHijo.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            // Enviar mensaje al processHijo a través del pipe.
            pw.println("Hola processHijo, soy tu padre!");
            pw.flush(); // asegurarse de que se envía el mensaje

            // Leer respuesta del processHijo
            // Se leen todas las líneas que el hijo envía como respuesta y se imprimen en la consola del padre.
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println("Padre recibe: " + linea);
            }

            processHijo.waitFor(); // esperar a que el processHijo termine
            System.out.println("Comunicación terminada.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


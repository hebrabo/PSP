import java.io.*;
// Importación de las clases de Apache Commons Net para el manejo del protocolo FTP
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTP;

public class Actividad2FTP {
    public static void main(String[] args) {

        // Control de argumentos: Se requiere al menos el host del servidor
        if (args.length < 1) {
            System.out.println("ERROR: indicar como parámetros:");
            System.out.println("servidor usuario(opcional) contraseña(opcional)");
            System.exit(1);
        }
        String servidorFTP = args[0];

        // Gestión de credenciales: anonymous por defecto si no se proporcionan
        String usuario = "anonymous", password = "";
        if (args.length >= 2) {
            usuario = args[1];
        }
        if (args.length >= 3) {
            password = args[2];
        }

        // Creación del objeto cliente que gestionará el canal de control
        FTPClient clienteFTP = new FTPClient();

        try {
            // Establecimiento de la conexión TCP inicial con el servidor (por defecto el puerto 21)
            clienteFTP.connect(servidorFTP);

            // Verificación del código de respuesta tras la conexión: Se espera un código 220 (Servicio listo)
            int codResp = clienteFTP.getReplyCode();

            // isPositiveCompletion comprueba si el código empieza por 2xx (Éxito)
            if (!FTPReply.isPositiveCompletion(codResp)) {
                System.out.printf("ERROR: Conexión rechazada con código de respuesta %d.\n", codResp);
                // El 2 indica que el problema fue que el servidor FTP no estaba listo o rechazó la conexión inicial
                System.exit(2);
            }

            /* Activación del MODO PASIVO: El cliente solicita al servidor que abra
               un puerto para el canal de datos. Evita bloqueos de firewalls locales.
            */
            clienteFTP.enterLocalPassiveMode();

            /* Configuración del tipo de transferencia a BINARIO:
               Indispensable para archivos que no son texto plano para evitar alteraciones de bits.
            */
            clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);

            if (usuario != null && password != null) {
                // Intento de autenticación mediante los comandos USER y PASS. Se espera un código de respuesta de 230
                boolean loginOK = clienteFTP.login(usuario, password);
                if (loginOK) {
                    System.out.printf("INFO: Login con usuario %s realizado.\n", usuario);

                    // ===========================================================
                    // ACTIVIDAD 2
                    // ===========================================================

                    // 1. Crear carpeta “desdeJava” (Creación de un nuevo directorio en la ruta actual del servidor)
                    if (clienteFTP.makeDirectory("desdeJava")) {
                        System.out.println("Punto 1: Carpeta 'desdeJava' creada.");
                    } else {
                        // Si devuelve false, lo más probable es que ya exista o no tengamos permisos
                        System.out.println("Punto 1: La carpeta 'desdeJava' no se ha creado (puede que ya exista).");
                    }

                    // 2. Entrar en la carpeta creada (Cambio del directorio de trabajo (CWD - Change Working Directory))
                    clienteFTP.changeWorkingDirectory("desdeJava");
                    // printWorkingDirectory devuelve la ruta absoluta actual en el servidor
                    System.out.println("Punto 2: Directorio actual: " + clienteFTP.printWorkingDirectory());

                    // 3. Almacenar documento “desdeElPrograma.txt” con contenido “texto” (Subida de un archivo al servidor (storeFile))
                    String texto = "texto";
                    // Convertimos el String en un flujo de entrada (InputStream) para transmitirlo.
                    // Uso de la clase ByteArrayInputStream para adaptar el contenido a un flujo de
                    // entrada, requisito del método storeFile.
                    InputStream is = new ByteArrayInputStream(texto.getBytes());
                    if (clienteFTP.storeFile("desdeElPrograma.txt", is)) {
                        System.out.println("Punto 3: Archivo 'desdeElPrograma.txt' guardado con éxito.");
                    } else {
                        // Si falla, informamos al usuario y mostramos el error técnico del servidor
                        System.err.println("Punto 3: ERROR al guardar el archivo.");
                        System.err.println("Respuesta del servidor: " + clienteFTP.getReplyString());
                    }
                    is.close();

                    /* 4. Recuperar el documento y mostrar por pantalla el contenido
                       (Recuperación del contenido del archivo (retrieveFileStream).
                       Abre un canal de datos para recibir el flujo de bytes del archivo.)
                    */
                    System.out.print("Punto 4: Contenido del fichero -> ");
                    InputStream rs = clienteFTP.retrieveFileStream("desdeElPrograma.txt");

                    if (rs != null) {
                        // Si el flujo no es nulo, leemos el contenido
                        BufferedReader reader = new BufferedReader(new InputStreamReader(rs));
                        String linea;
                        while ((linea = reader.readLine()) != null) {
                            System.out.println(linea);
                        }
                        reader.close();

                        // IMPORTANTE: completePendingCommand es necesario para finalizar
                        // la transacción de datos y poder enviar nuevos comandos.
                        if (!clienteFTP.completePendingCommand()) {
                            System.err.println("Error al completar la transferencia del archivo.");
                        }
                    } else {
                        // Si es null, informamos del error exacto del servidor
                        System.out.println("No se pudo recuperar el archivo.");
                        System.err.println("Respuesta del servidor: " + clienteFTP.getReplyString());
                    }

                    // 5. Recorrer los ficheros de la carpeta actual (desdeJava) (Listado de elementos mediante el comando LIST)
                    System.out.println("Punto 5: Contenido de la carpeta 'desdeJava':");
                    // Devuelve un array de objetos FTPFile con la información de cada entrada
                    FTPFile[] ficherosHijo = clienteFTP.listFiles();
                    for (FTPFile f : ficherosHijo) {
                        System.out.println("   - " + f.getName());
                    }

                    // 6. Volver al directorio padre (Navegación jerárquica: Volver al directorio inmediatamente superior (Padre))
                    clienteFTP.changeToParentDirectory();
                    System.out.println("Punto 6: Volviendo al directorio padre...");

                    // 7. Volver a recorrer los ficheros del directorio actual (Raíz) (Listado final para verificar la posición en el directorio raíz)
                    System.out.println("Punto 7: Contenido del directorio padre:");
                    FTPFile[] ficherosPadre = clienteFTP.listFiles();
                    for (FTPFile f : ficherosPadre) {
                        // f.isDirectory() permite distinguir carpetas de archivos regulares
                        System.out.println("   - " + f.getName() + (f.isDirectory() ? "/" : ""));
                    }

                    // ===========================================================
                    // FIN ACTIVIDAD 2
                    // ===========================================================

                } else {
                    System.out.printf("ERROR: Login con usuario %s rechazado.\n", usuario);
                    return;
                }
            }

        } catch (IOException e) {
            // Captura de errores de red o de permisos durante la ejecución
            // Cambiamos el mensaje para que sea más general, ya que puede fallar al crear la carpeta
            System.out.println("ERROR: Ocurrió un fallo en la comunicación con el servidor");
            e.printStackTrace();
            return;
        } finally {
            // Bloque de cierre: Se asegura la desconexión incluso si hubo errores previos
            if (clienteFTP.isConnected()) { // Quitamos el check de null que daba aviso
                try {
                    // Envío del comando QUIT (protocolo FTP) para cerrar la sesión de forma ordenada
                    clienteFTP.logout();

                    // Cierre físico de la conexión TCP (Capa de transporte)
                    clienteFTP.disconnect();

                    System.out.println("INFO: Sesión finalizada y conexión cerrada.");
                } catch (IOException e) {
                    System.out.println("AVISO: No se pudo cerrar la conexión limpiamente.");
                }
            }
        }
    }
}

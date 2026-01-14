import java.io.IOException;
// Importación de clases de la librería Apache Commons Net para servicios en red
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTP;

public class ListaFicherosFTP {

    public static void main(String[] args) {

        // Validación de parámetros: El protocolo FTP requiere una dirección de host (servidor)
        if (args.length < 1) {
            System.out.println("ERROR: indicar como parámetros:");
            System.out.println("servidor usuario(opcional) contraseña(opcional)");
            System.exit(1);
        }
        String servidorFTP = args[0];

        // Definición de credenciales. Por defecto se usa el acceso "anonymous" (público)
        String usuario = "anonymous", password = "";
        if (args.length >= 2) {
            usuario = args[1];
        }
        if (args.length >= 3) {
            password = args[2];
        }

        // Instanciación del objeto cliente que gestiona el protocolo de nivel de aplicación
        FTPClient clienteFTP = new FTPClient();

        try {
            // El cliente inicia una conexión TCP al servidor (habitualmente al puerto 21)
            clienteFTP.connect(servidorFTP);

            /* El protocolo FTP es orientado a conexión y responde con códigos numéricos.
               Tras conectar, obtenemos el código de estado (Reply Code) del servidor.
            */
            int codResp = clienteFTP.getReplyCode();

            // isPositiveCompletion verifica códigos 2xx, indicando que el servicio está listo
            if (!FTPReply.isPositiveCompletion(codResp)) {
                System.out.printf("ERROR: Conexión rechazada con código de respuesta %d.\n", codResp);
                System.exit(2);
            }

            /* Configuración de MODO PASIVO: El cliente solicita al servidor que abra un puerto
               para el canal de datos. Es vital para evitar bloqueos por cortafuegos (firewalls).
            */
            clienteFTP.enterLocalPassiveMode();

            /* Configuración del tipo de transferencia a BINARIO (TYPE I).
               Garantiza que el intercambio de bits sea exacto y no se altere el contenido.
            */
            clienteFTP.setFileType(FTP.BINARY_FILE_TYPE);

            if (usuario != null && password != null) {
                // Procedimiento de autenticación mediante el intercambio de comandos USER y PASS
                boolean loginOK = clienteFTP.login(usuario, password);
                if (loginOK) {
                    System.out.printf("INFO: Login con usuario %s realizado.\n", usuario);
                }
                else {
                    System.out.printf("ERROR: Login con usuario %s rechazado.\n", usuario);
                    return;
                }
            }

            // Muestra el mensaje de bienvenida enviado por el servidor (banner del protocolo)
            System.out.printf("INFO: Conexión establecida, mensaje de bienvenida del servidor:\n====\n%s\n====\n", clienteFTP.getReplyString());

            // Comando PWD: Se consulta al servidor la ruta del directorio de trabajo actual
            System.out.printf("INFO: Directorio actual en servidor: %s, contenidos:\n", clienteFTP.printWorkingDirectory());

            /* Comando LIST: Se abre un canal de datos temporal para transferir
               el listado de ficheros y carpetas de la ubicación actual.
            */
            FTPFile[] fichServ = clienteFTP.listFiles();
            for(FTPFile f: fichServ) {
                String infoAdicFich = "";

                // Determinamos si el elemento es un directorio (DIRECTORY_TYPE)
                if(f.getType() == FTPFile.DIRECTORY_TYPE) {
                    infoAdicFich = "/";
                }
                // Determinamos si es un enlace simbólico (acceso directo en el servidor)
                else if(f.getType() == FTPFile.SYMBOLIC_LINK_TYPE) {
                    infoAdicFich = " -> " + f.getLink();
                }

                // Imprimimos el nombre del objeto junto a su identificador visual
                System.out.printf("%s%s\n", f.getName(), infoAdicFich);
            }


        } catch (IOException e) {
            // Gestión de excepciones de red (ej. servidor caído o timeout)
            System.out.println("ERROR: conectando al servidor");
            e.printStackTrace();
            return;
        } finally {
            // El bloque finally asegura la liberación de recursos de red
            if (clienteFTP != null) {
                try {
                    // Cierre ordenado de la sesión y desconexión física del socket
                    clienteFTP.disconnect();
                    System.out.println("INFO: conexión cerrada.");
                } catch (IOException e) {
                    System.out.println("AVISO: no se pudo cerrar la conexión.");
                }
            }
        }
    }
}
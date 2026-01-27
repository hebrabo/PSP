import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File; // Necesario para el adjunto
import java.util.Properties;


public class SendEmail {

    public void mandarCorreo() {
        // El correo gmail de envío
        String correoEnvia = "hebrabo@gmail.com";
        String claveCorreo = "gmcf mtqp belj xhil";

        // La configuración para enviar correo
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.user", correoEnvia);
        properties.put("mail.password", claveCorreo);

        // Obtener la sesion
        Session session = Session.getInstance(properties, null);

        try {
            // Crear el cuerpo del mensaje
            MimeMessage mimeMessage = new MimeMessage(session);

            // Agregar quien envía el correo
            mimeMessage.setFrom(new InternetAddress(correoEnvia));

            // Los destinatarios
            InternetAddress[] internetAddresses = {
                    new InternetAddress("hebrabo@gmail.com"),
                    new InternetAddress("helbrabou@alu.edu.gva.es")};

            // Agregar los destinatarios al mensaje
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    internetAddresses);

            // Agregar el asunto al correo
            // CAMBIO 1: Asunto de bienvenida
            mimeMessage.setSubject("¡Bienvenida a nuestra aplicación!");

            // Creo la parte del mensaje
            // CAMBIO 2: Cuerpo del mensaje en formato HTML
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            String htmlContenido = "<h1>¡Bienvenido!</h1><p>Gracias por registrarte en nuestra plataforma.</p>";
            mimeBodyPart.setContent(htmlContenido, "text/html; charset=utf-8");

            // CAMBIO 3: Parte del archivo adjunto
            MimeBodyPart adjunto = new MimeBodyPart();
            adjunto.attachFile(new File("imagen_bienvenida.jpg"));

            // Crear el multipart y agregar ambas partes
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart); // Agregamos el HTML
            multipart.addBodyPart(adjunto); // Agregamos la image

            // Agregar el multipart al cuerpo del mensaje
            mimeMessage.setContent(multipart);

            // Enviar el mensaje
            Transport transport = session.getTransport("smtp");
            transport.connect(correoEnvia, claveCorreo);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Correo de bienvenida enviado con éxito desde " + correoEnvia);
    }

    public static void main(String[] args) {
        SendEmail correoTexto = new SendEmail();
        correoTexto.mandarCorreo();
    }
}


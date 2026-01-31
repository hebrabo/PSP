import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;

public class HybridEncryptionExample {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        // 1. Solicitar al usuario que introduzca un mensaje
        System.out.print("Introduce un mensaje para cifrar: ");
        String mensajeOriginal = scanner.nextLine();

        System.out.println("Mensaje a procesar: " + mensajeOriginal);

        // --- PASO A: CIFRADO SIMÉTRICO (AES) DEL MENSAJE ---

        // 2. Cifrar el mensaje con AES
        // Clave personalizada (debe tener 16 bytes para AES-128)
        String clavePersonalizada = "claveSecreta1234";
        byte[] claveBytes = clavePersonalizada.getBytes();
        SecretKey secretKey = new SecretKeySpec(claveBytes, "AES");

        // Cifrar el mensaje
        byte[] mensajeCifrado = encrypt(mensajeOriginal, secretKey);

        // --- PASO B: CIFRADO ASIMÉTRICO (RSA) DE LA CLAVE ---

        // 3. Generar un par de claves RSA
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // System.out.println("Mensaje original: " + mensajeOriginal);

        // 4. Cifrar la clave AES con la clave pública RSA
        // Obtenemos los bytes de la clave AES
        byte[] claveAESEnBytes = secretKey.getEncoded();

        // Ciframos LA CLAVE con RSA
        byte[] claveAESCifrada = cifrarConRSA(claveAESEnBytes, publicKey);

        // 5. Mostrar por pantalla el mensaje cifrado y la clave AES cifrada
        System.out.println("\n--- RESULTADOS DEL CIFRADO ---");
        System.out.println("Mensaje cifrado (AES): " + Base64.getEncoder().encodeToString(mensajeCifrado));
        System.out.println("Clave AES Cifrada (RSA): " + Base64.getEncoder().encodeToString(claveAESCifrada));

        // --- PASO C: DESCIFRADO ---

        // 6. Descifrar la clave AES con la clave privada
        // Esto nos devuelve los bytes originales ("claveSecreta1234")
        byte[] claveAESDescifradaBytes = descifrarConRSA(claveAESCifrada, privateKey);

        // Reconstruimos la clave AES a partir de los bytes descifrados
        SecretKey claveAESRecuperada = new SecretKeySpec(claveAESDescifradaBytes, "AES");

        // 7. Descifrar el mensaje utilizando la clave simétrica descifrada (reconstruida)
        String decryptedMessage = decrypt(mensajeCifrado, claveAESRecuperada);

        // 8. Mostrar por pantalla el mensaje descifrado
        System.out.println("\n--- RESULTADO DEL DESCIFRADO ---");
        System.out.println("Mensaje descifrado final: " + decryptedMessage);
    }

    // --- MÉTODOS AUXILIARES ---

    public static byte[] encrypt(String mensajeOriginal, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(mensajeOriginal.getBytes());
    }

    public static String decrypt(byte[] encryptedMessage, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedMessage);
        return new String(decryptedBytes);
    }

    private static byte[] cifrarConRSA(byte[] datos, PublicKey clavePublica) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, clavePublica);
        return cipher.doFinal(datos);
    }

    private static byte[] descifrarConRSA(byte[] datosCifrados, PrivateKey clavePrivada) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, clavePrivada);
        return cipher.doFinal(datosCifrados);
    }
}
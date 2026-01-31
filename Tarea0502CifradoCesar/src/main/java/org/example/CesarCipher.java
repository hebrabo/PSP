package org.example;

public class CesarCipher {
    public static String cifrar(String mensaje, int desplazamiento) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < mensaje.length(); i++) {
            char caracter = mensaje.charAt(i);

            if (Character.isLetter(caracter)) {
                char base = Character.isLowerCase(caracter) ? 'a' : 'A';
                caracter = (char) ((caracter - base + desplazamiento) % 26 + base);
            }

            resultado.append(caracter);
        }

        return resultado.toString();
    }

    public static String descifrar(String mensajeCifrado, int desplazamiento) {
        return cifrar(mensajeCifrado, 26 - desplazamiento); // Descifrar es cifrar con el desplazamiento opuesto
    }

    public static void main(String[] args) {
        String mensajeOriginal = "HELENA";
        int desplazamiento = 3;

        // Cifrar
        String mensajeCifrado = cifrar(mensajeOriginal, desplazamiento);
        System.out.println("Mensaje Cifrado: " + mensajeCifrado);

        // Descifrar
        String mensajeDescifrado = descifrar(mensajeCifrado, desplazamiento);
        System.out.println("Mensaje Descifrado: " + mensajeDescifrado);
    }
}

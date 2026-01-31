package org.example;

/*
 * FUNCIONAMIENTO:
 * 1. Clave: El valor de desplazamiento cambia para cada letra.
 * - Para la primera letra: Se usa una 'clave inicial' numérica proporcionada por el usuario.
 * - Para las letras siguientes: La clave de desplazamiento toma el valor del índice de la letra anterior.
 *
 * 2. Alfabeto: Utilizo el alfabeto español que tiene 27 caracteres que incluye la letra 'Ñ'.
 */

public class MiCifrado {
    // Defino el alfabeto español explícitamente para manejar la Ñ
    private static final String ALFABETO = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

    public static String cifrar(String mensaje, int clave) {
        StringBuilder resultado = new StringBuilder();
        mensaje = mensaje.toUpperCase();

        int desplazamientoActual = clave; // El primer desplazamiento es la clave

        for (int i = 0; i < mensaje.length(); i++) {
            char caracter = mensaje.charAt(i);

            // Buscamos la posición del carácter en el alfabeto español
            int indiceOriginal = ALFABETO.indexOf(caracter);

            if (indiceOriginal != -1) {
                /*
                 * Fórmula: Sumamos el desplazamiento a la posición original.
                 * El operador módulo (%) asegura que si la suma supera el final del alfabeto (26),
                 * el índice"da la vuelta" y vuelve a empezar desde la 'A' (0).
                 */
                int nuevoIndice = (indiceOriginal + desplazamientoActual) % ALFABETO.length();

                char caracterCifrado = ALFABETO.charAt(nuevoIndice);
                resultado.append(caracterCifrado);

                // ACTUALIZACIÓN CLAVE:
                // El desplazamiento para la siguiente letra será el índice de la letra actual
                desplazamientoActual = indiceOriginal;
            } else {
                // Si no es una letra (espacio, punto, etc), lo dejamos igual
                resultado.append(caracter);
            }
        }
        return resultado.toString();
    }

    public static String descifrar(String mensajeCifrado, int clave) {
        StringBuilder resultado = new StringBuilder();
        mensajeCifrado = mensajeCifrado.toUpperCase();

        int desplazamientoActual = clave; // Empezamos igual, con la clave

        for (int i = 0; i < mensajeCifrado.length(); i++) {
            char caracter = mensajeCifrado.charAt(i);
            int indiceCifrado = ALFABETO.indexOf(caracter);

            if (indiceCifrado != -1) {
                // Fórmula Inversa: (Posición Cifrada - Desplazamiento)
                int indiceOriginal = indiceCifrado - desplazamientoActual;

                // Ajuste de módulo para números negativos
                if (indiceOriginal < 0) {
                    indiceOriginal += ALFABETO.length();
                }

                char caracterDescifrado = ALFABETO.charAt(indiceOriginal);
                resultado.append(caracterDescifrado);

                // ACTUALIZACIÓN CLAVE:
                // Para descifrar la siguiente, usamos el índice de la letra que acabamos de recuperar
                desplazamientoActual = indiceOriginal;
            } else {
                resultado.append(caracter);
            }
        }
        return resultado.toString();
    }

    public static void main(String[] args) {
        // Prueba con una palabra que contiene Ñ
        String mensajeOriginal = "MAÑANA, ESPERO, QUE HAGA SOL";
        int clave = 5; // Empezamos desplazando 5 (F)

        System.out.println("--- Cifrado clave (Alfabeto Español) ---");
        System.out.println("Mensaje Original: " + mensajeOriginal);
        System.out.println("Clave inicial:  " + clave);

        // Cifrar
        String mensajeCifrado = cifrar(mensajeOriginal, clave);
        System.out.println("Mensaje Cifrado:  " + mensajeCifrado);

        // Explicación rápida de la primera letra 'M' (índice 12):
        // 12 + 5 (clave) = 17 -> Q.
        // Siguiente letra 'A' (0) usará el valor de 'M' (12) como clave.

        // Descifrar
        String mensajeDescifrado = descifrar(mensajeCifrado, clave);
        System.out.println("Mensaje Descifrado: " + mensajeDescifrado);
    }
}

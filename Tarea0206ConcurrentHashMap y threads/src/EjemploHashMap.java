import java.util.HashMap;
import java.util.Map;

public class EjemploHashMap {
    public static void main(String[] args) {
        // Texto de ejemplo
        String texto = "hola mundo hola java";

        // Crear el HashMap (clave = palabra, valor = veces que aparece)
        Map<String, Integer> contador = new HashMap<>();

        // Dividir el texto por espacios y recorrer cada palabra
        for (String palabra : texto.split(" ")) {
            // Incrementar el contador de esa palabra
            contador.put(palabra, contador.getOrDefault(palabra, 0) + 1);
        }

        // Mostrar resultados
        System.out.println("Frecuencia de palabras:");
        for (Map.Entry<String, Integer> entrada : contador.entrySet()) {
            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }
    }
}
package mistareas;

import mistareas.entidades.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ClientePruebaMisTareas {
    private static final String BASE_URL = "http://localhost:8080/tasks";

    public static void main(String[] args) {
        // Instancia de RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Obtener todas las tareas
        ResponseEntity<String> responseAll = restTemplate.getForEntity(BASE_URL, String.class);
        System.out.println("Obtener todas las tareas:");
        System.out.println(responseAll.getBody());
        System.out.println();

        // Obtener una tarea por ID (supongamos que la tarea con ID 1 existe)
        Long taskId = 1L;
        ResponseEntity<String> responseById = restTemplate.getForEntity(BASE_URL + "/" + taskId, String.class);
        System.out.println("Obtener tarea por ID:");
        System.out.println(responseById.getBody());
        System.out.println();
    }
}

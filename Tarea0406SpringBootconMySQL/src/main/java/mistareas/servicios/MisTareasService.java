package mistareas.servicios;

import mistareas.entidades.Task;
import java.util.List;

/**
 * CAPA DE SERVICIO (Fachada):
 * Esta interfaz define la "Lógica de Negocio" de nuestra aplicación.
 * Actúa como un puente intermedio entre el Controlador (que recibe la web)
 * y el Repositorio (que habla con la base de datos).
 */
public interface MisTareasService {

    /**
     * Obtener todas las tareas:
     * Solicita al repositorio la lista completa de registros.
     */
    List<Task> getMisTareas();

    /**
     * Obtener una tarea específica:
     * Permite buscar un registro concreto por su clave primaria (ID).
     */
    Task getMiTarea(Long id);

    /**
     * Guardar (Create):
     * Define la operación para dar de alta una nueva tarea en el sistema.
     */
    int save(Task task);

    /**
     * Actualizar (Update):
     * Define la operación para modificar los datos de una tarea ya existente.
     */
    int update(Task task);

    /**
     * Borrar (Delete):
     * Define la operación para eliminar una tarea del sistema usando su identificador.
     */
    int deleteById(Long id);
}
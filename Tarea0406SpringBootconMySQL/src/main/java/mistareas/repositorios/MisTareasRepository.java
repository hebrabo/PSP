package mistareas.repositorios;

import mistareas.entidades.Task;
import java.util.List;

/**
 * INTERFAZ: Actúa como un contrato legal entre las capas de la aplicación.
 * Define las operaciones permitidas sobre la base de datos 'mytasks'.
 * No contiene código SQL, solo las firmas de los métodos.
 */
public interface MisTareasRepository {

    /**
     * READ (Leer todo):
     * @return Una lista con todos los objetos Task encontrados en la tabla.
     */
    List<Task> getMisTareas();

    /**
     * READ (Leer uno):
     * @param id El identificador único de la tarea.
     * @return El objeto Task correspondiente o null si no existe.
     */
    Task getMiTarea(Long id);

    /**
     * CREATE (Crear):
     * @param task Objeto con la descripción y estado a insertar.
     * @return El número de filas afectadas en MySQL (debería ser 1).
     */
    int save(Task task);

    /**
     * UPDATE (Actualizar):
     * @param task Objeto que contiene el ID de la tarea a buscar y los nuevos datos.
     * @return El número de filas modificadas.
     */
    int update(Task task);

    /**
     * DELETE (Borrar):
     * @param id El ID de la fila que queremos eliminar permanentemente.
     * @return El número de filas eliminadas.
     */
    int deleteById(Long id);
}

package mistareas.repositorios;

import mistareas.entidades.Task;

import java.util.List;

public interface MisTareasRepository {
    List<Task> getMisTareas();
    Task getMiTarea(Long id);

    // Guardar
    int save(Task task);
    // Editar
    int update(Task task);
    // Borrar
    int deleteById(Long id);
}

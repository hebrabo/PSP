package mistareas.servicios;

import mistareas.entidades.Task;

import java.util.List;

public interface MisTareasService {
    List<Task> getMisTareas();
    Task getMiTarea(Long id);

    // Firma del metodo save
    int save(Task task);

    // Firma del método editar
    int update(Task task);

    // Firma del método Borrar
    int deleteById(Long id);
}

package mistareas.repositorios;

import mistareas.entidades.Task;

import java.util.List;

public interface MisTareasRepository {
    List<Task> getMisTareas();
    Task getMiTarea(Long id);
}

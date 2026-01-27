package mistareas.servicios;

import mistareas.entidades.Task;

import java.util.List;

public interface MisTareasService {
    List<Task> getMisTareas();
    Task getMiTarea(Long id);
}

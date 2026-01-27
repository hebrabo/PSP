package mistareas.servicios;

import mistareas.entidades.Task;
import mistareas.repositorios.MisTareasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MisTareasServiceImpl implements  MisTareasService{
    private final MisTareasRepository misTareasRepository;

    @Autowired
    public MisTareasServiceImpl(MisTareasRepository misTareasRepository){
        this.misTareasRepository  = misTareasRepository;
    }

    @Override
    public List<Task> getMisTareas() {
        return misTareasRepository.getMisTareas();
    }

    @Override
    public Task getMiTarea(Long id) {
        return misTareasRepository.getMiTarea(id);
    }

    // Llamada Guardar
    @Override
    public int save(Task task) {
        // El servicio le pide al repositorio que guarde la tarea
        return misTareasRepository.save(task);
    }

    // Llamada Editar
    @Override
    public int update(Task task) {
        return misTareasRepository.update(task);
    }

    // Llamada Borrar
    @Override
    public int deleteById(Long id) {
        return misTareasRepository.deleteById(id);
    }
}

package mistareas.servicios;

import mistareas.entidades.Task;
import mistareas.repositorios.MisTareasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Service: Indica que esta clase contiene la lógica de negocio.
 * Spring la registra en su "contenedor" para que pueda ser inyectada en el Controlador.
 */
@Service
public class MisTareasServiceImpl implements MisTareasService {

    // Referencia al repositorio (la capa que habla con MySQL)
    private final MisTareasRepository misTareasRepository;

    /**
     * @Autowired: Inyección de Dependencias por constructor.
     * Spring busca el "MisTareasRepositoryImpl" y lo conecta aquí automáticamente.
     */
    @Autowired
    public MisTareasServiceImpl(MisTareasRepository misTareasRepository){
        this.misTareasRepository = misTareasRepository;
    }

    /**
     * READ: Solicita al repositorio la lista de todas las tareas.
     */
    @Override
    public List<Task> getMisTareas() {
        return misTareasRepository.getMisTareas();
    }

    /**
     * READ: Solicita una tarea específica por su ID.
     */
    @Override
    public Task getMiTarea(Long id) {
        return misTareasRepository.getMiTarea(id);
    }

    /**
     * CREATE: Pide al repositorio que inserte una nueva fila en la tabla 'task'.
     * @return 1 si se guardó correctamente.
     */
    @Override
    public int save(Task task) {
        return misTareasRepository.save(task);
    }

    /**
     * UPDATE: Pide al repositorio que actualice los datos de una tarea existente.
     */
    @Override
    public int update(Task task) {
        return misTareasRepository.update(task);
    }

    /**
     * DELETE: Pide al repositorio que elimine el registro con el ID indicado.
     */
    @Override
    public int deleteById(Long id) {
        return misTareasRepository.deleteById(id);
    }
}
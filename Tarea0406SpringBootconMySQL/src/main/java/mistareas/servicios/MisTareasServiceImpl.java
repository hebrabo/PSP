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
}

package mistareas.controladores;

import mistareas.entidades.Task;
import mistareas.servicios.MisTareasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class MisTareasController {
    private final MisTareasService misTareasService;

    @Autowired
    public MisTareasController(MisTareasService misTareasService){
        this.misTareasService = misTareasService;
    }

    @GetMapping
    public List<Task> obtenerTodos() {
        return misTareasService.getMisTareas();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> obtenerPorId(@PathVariable Long id) {
        Task tarea = misTareasService.getMiTarea(id);

        if (tarea != null) {
            return new ResponseEntity<>(tarea, HttpStatus.OK);
        } else {
            // Enviar una respuesta HTTP 404 si la tarea no se encuentra
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

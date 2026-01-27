package mistareas.controladores;

import mistareas.entidades.Task;
import mistareas.servicios.MisTareasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
/*
 * @PostMapping: Indica que este método solo responderá cuando alguien
 *               envíe datos (un "POST") a la ruta /tasks.
 * @RequestBody: Le dice a Spring Boot: "Coge el JSON que viene en el cuerpo
 * del mensaje y conviértelo automáticamente en un objeto Java de tipo Task".
 */
    @PostMapping
    // Método para RECIBIR una nueva tarea
    public int crearTarea(@RequestBody Task nuevaTarea) {
        // Llamamos al servicio para que inicie el proceso de guardado
        return misTareasService.save(nuevaTarea);
    }

    // PUT (actualizar)
    @PutMapping("/{id}")
    public int actualizarTarea(@PathVariable Long id, @RequestBody Task tarea) {
        // Forzamos que la tarea use el ID que viene en la URL
        tarea.setId(id);
        return misTareasService.update(tarea);
    }

    // DELETE (borrar)
    @DeleteMapping("/{id}")
    public int eliminarTarea(@PathVariable Long id) {
        return misTareasService.deleteById(id);
    }
}

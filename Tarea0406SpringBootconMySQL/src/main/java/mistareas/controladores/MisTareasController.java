package mistareas.controladores;

import mistareas.entidades.Task;
import mistareas.servicios.MisTareasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @RestController: Combina @Controller y @ResponseBody. Indica que esta clase
 * manejará peticiones REST y que las respuestas se enviarán directamente en el
 * cuerpo del mensaje (normalmente como JSON).
 */
@RestController
@RequestMapping("/tasks") // Define la ruta base (/tasks) para todos los métodos de esta clase.
public class MisTareasController {

    private final MisTareasService misTareasService;

    /**
     * @Autowired: Realiza la "Inyección de Dependencias". Spring busca un objeto que implemente
     * MisTareasService y lo "enchufa" aquí automáticamente al arrancar.
     */
    @Autowired
    public MisTareasController(MisTareasService misTareasService){
        this.misTareasService = misTareasService;
    }

    /**
     * @GetMapping: Responde a peticiones HTTP GET.
     * Se usa para LEER (Read) la lista completa de tareas.
     */
    @GetMapping
    public List<Task> obtenerTodos() {
        return misTareasService.getMisTareas();
    }

    /**
     * @GetMapping("/{id}"): El {id} es una variable dinámica en la URL.
     * @PathVariable: Extrae ese valor de la URL y lo pasa como parámetro al método.
     * ResponseEntity: Nos permite personalizar el código de estado HTTP (200 OK o 404 Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> obtenerPorId(@PathVariable Long id) {
        Task tarea = misTareasService.getMiTarea(id);

        if (tarea != null) {
            return new ResponseEntity<>(tarea, HttpStatus.OK);
        } else {
            // Si la tarea no existe en MySQL, devolvemos un error 404 (Not Found)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @PostMapping: Responde a peticiones HTTP POST.
     * Se usa para CREAR (Create) una nueva tarea en la base de datos.
     * @RequestBody: Deserializa el JSON que enviamos desde Postman y lo convierte
     * en un objeto Task de Java automáticamente.
     */
    @PostMapping
    public int crearTarea(@RequestBody Task nuevaTarea) {
        // Devuelve el número de filas afectadas (normalmente 1 si tiene éxito).
        return misTareasService.save(nuevaTarea);
    }

    /**
     * @PutMapping("/{id}"): Responde a peticiones HTTP PUT.
     * Se usa para ACTUALIZAR (Update) una tarea existente.
     */
    @PutMapping("/{id}")
    public int actualizarTarea(@PathVariable Long id, @RequestBody Task tarea) {
        // Es vital asignar el ID de la URL al objeto tarea antes de mandarlo al servicio
        // para asegurar que actualizamos la fila correcta en MySQL.
        tarea.setId(id);
        return misTareasService.update(tarea);
    }

    /**
     * @DeleteMapping("/{id}"): Responde a peticiones HTTP DELETE.
     * Se usa para BORRAR (Delete) un registro por su identificador único.
     */
    @DeleteMapping("/{id}")
    public int eliminarTarea(@PathVariable Long id) {
        return misTareasService.deleteById(id);
    }
}
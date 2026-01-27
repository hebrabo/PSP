package mistareas.repositorios;

import mistareas.entidades.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @Repository: Indica a Spring que esta clase es un componente de acceso a datos.
 * Spring la detecta automáticamente y traduce las excepciones de SQL a excepciones de Spring.
 */
@Repository
public class MisTareasRepositoryImpl implements MisTareasRepository {

    /**
     * JdbcTemplate: Es la herramienta de Spring que simplifica el uso de JDBC.
     * Se encarga de abrir/cerrar conexiones y ejecutar las sentencias SQL de forma segura.
     */
    private final JdbcTemplate jdbcTemplate;

    // Inyección por constructor: Spring "enchufa" el JdbcTemplate configurado en application.properties.
    public MisTareasRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * SELECT ALL: Recupera todas las filas de la tabla 'task'.
     * Usamos un "RowMapper" (la lambda) para decirle a Java cómo convertir cada fila
     * del ResultSet en un objeto Task.
     */
    @Override
    public List<Task> getMisTareas() {
        String sql = "SELECT id, description, status FROM task";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new Task(
                        resultSet.getLong("id"),
                        resultSet.getString("description"),
                        resultSet.getString("status")
                )
        );
    }

    /**
     * SELECT BY ID: Busca una tarea específica.
     * El '?' es un marcador de posición que evita ataques de inyección SQL.
     */
    @Override
    public Task getMiTarea(Long id) {
        String sql = "SELECT id, description, status FROM task WHERE id = ?";
        // Pasamos el id en un array de objetos para sustituir el '?'
        List<Task> tareas = jdbcTemplate.query(sql, new Object[]{id}, (resultSet, rowNum) ->
                new Task(
                        resultSet.getLong("id"),
                        resultSet.getString("description"),
                        resultSet.getString("status")
                )
        );

        // Si la lista está vacía, devolvemos null; si no, el primer resultado.
        return tareas.isEmpty() ? null : tareas.get(0);
    }

    /**
     * INSERT: Crea un nuevo registro.
     * Usamos jdbcTemplate.update() para cualquier operación que modifique la tabla (INSERT, UPDATE, DELETE).
     */
    @Override
    public int save(Task task) {
        String sql = "INSERT INTO task (description, status) VALUES (?, ?)";
        // Devuelve el número de filas insertadas (normalmente 1).
        return jdbcTemplate.update(sql, task.getDescription(), task.getStatus());
    }

    /**
     * UPDATE: Modifica una tarea existente basándose en su ID.
     */
    @Override
    public int update(Task task) {
        String sql = "UPDATE task SET description = ?, status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, task.getDescription(), task.getStatus(), task.getId());
    }

    /**
     * DELETE: Elimina permanentemente una fila por su ID.
     */
    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM task WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Plato;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
    Optional<Plato> findByNombre(@NotBlank String nombre);

    // Consulta para obtener el promedio de puntuaciones de un plato
    /**
     * Metodo para calcular la puntuación media de un plato basado en las puntuaciones registradas.
     * Este metodo consulta la tabla de puntuaciones (Puntuacion) para calcular el promedio.
     *
     * @param platoId ID del plato.
     * @return Promedio de puntuación del plato (o null si no hay puntuaciones).
     */
    @Query("SELECT AVG(p.puntuacion) FROM Puntuacion p WHERE p.tipoEntidad = 'PLATO' AND p.idEntidad = :platoId")
    Double findAveragePuntuacionByPlatoId(@Param("platoId") Long platoId);

    // Metodo para encontrar platos aprobados y no eliminados
    List<Plato> findByEstadoTrueAndEliminadoFalse();

    // Consulta para obtener todas las puntuaciones de un plato
    List<Plato> findAllByEliminadoFalse(); // Devuelve solo los platos no eliminados

    // Consulta para obtener todos los platos no aprobados
    List<Plato> findByEstadoFalse();

    // Consulta para obtener todos los platos eliminados
    List<Plato> findByEliminadoTrue();

    // Consulta para obtener todos los platos no aprobados y no eliminados
    List<Plato> findByEstadoFalseAndEliminadoFalse();


}
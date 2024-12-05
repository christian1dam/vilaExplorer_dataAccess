package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Plato;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Long> {
    Optional<Plato> findByNombre(@NotBlank String nombre);

    // Consulta para obtener el promedio de puntuaciones de un plato
    @Query("SELECT AVG(p.puntuacion) FROM Puntuacion p WHERE p.tipoEntidad = 'PLATO' AND p.idEntidad = :platoId")
    Double findAveragePuntuacionByPlatoId(@Param("platoId") Long platoId);
}
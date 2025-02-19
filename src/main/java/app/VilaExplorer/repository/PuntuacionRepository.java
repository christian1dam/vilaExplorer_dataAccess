package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Puntuacion;
import app.VilaExplorer.enums.TipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PuntuacionRepository extends JpaRepository<Puntuacion, Long> {

    // Encuentra todas las puntuaciones de una entidad específica por ID y tipo de entidad
    List<Puntuacion> findByIdEntidadAndTipoEntidad(Long idEntidad, TipoEntidad tipoEntidad);

    // Encuentra todas las puntuaciones realizadas por un usuario en una entidad específica
    List<Puntuacion> findByUsuario_IdUsuarioAndIdEntidadAndTipoEntidad(Long idUsuario, Long idEntidad, TipoEntidad tipoEntidad);

    // Encuentra todas las puntuaciones realizadas por un usuario en todas las entidades
    List<Puntuacion> findByUsuario_IdUsuario(Long idUsuario);

    // Encuentra todas las puntuaciones de un tipo de entidad específico (por ejemplo, todos los platos, todos los lugares)
    List<Puntuacion> findByTipoEntidad(TipoEntidad tipoEntidad);

    // Metodo adicional: Calcular promedio de puntuación de una entidad específica
    @Query("SELECT AVG(p.puntuacion) FROM Puntuacion p WHERE p.idEntidad = :idEntidad AND p.tipoEntidad = :tipoEntidad")
    Optional<Double> findAveragePuntuacionByEntidad(@Param("idEntidad") Long idEntidad, @Param("tipoEntidad") TipoEntidad tipoEntidad);

    // Metodo adicional: Contar puntuaciones agrupadas por estrellas para una entidad específica
    @Query("SELECT p.puntuacion, COUNT(p) FROM Puntuacion p WHERE p.idEntidad = :idEntidad AND p.tipoEntidad = :tipoEntidad GROUP BY p.puntuacion")
    List<Object[]> findPuntuacionCountByEntidad(@Param("idEntidad") Long idEntidad, @Param("tipoEntidad") TipoEntidad tipoEntidad);
}

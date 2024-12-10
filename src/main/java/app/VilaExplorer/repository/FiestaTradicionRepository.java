package app.VilaExplorer.repository;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.Usuario;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiestaTradicionRepository extends JpaRepository<FiestaTradicion, Long> {


    List<FiestaTradicion> findByAutor_IdUsuario(Usuario autor);

    @Query("SELECT f FROM FiestaTradicion f WHERE f.activo = true AND f.autor.idUsuario = :idAutor")
    List<FiestaTradicion> findActiveByAutor(@Param("idAutor") Long idAutor);

    @Query("SELECT f FROM FiestaTradicion f WHERE LOWER(f.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<FiestaTradicion> searchByKeywordList(@Param("keyword") String keyword);

    @Query("SELECT f FROM FiestaTradicion f WHERE LOWER(f.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<FiestaTradicion> searchByKeywordPage(@Param("keyword") String keyword, Pageable pageable);

    List<FiestaTradicion> findByNombre(@NotBlank String nombre);

    @Query("SELECT f FROM FiestaTradicion f WHERE f.activo = true")
    List<FiestaTradicion> findAllActive();

    @Query("SELECT f FROM FiestaTradicion f WHERE f.activo = true AND " +
            "(LOWER(f.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<FiestaTradicion> searchActiveByKeywordList(@Param("keyword") String keyword);

    @Query("SELECT f FROM FiestaTradicion f WHERE f.activo = true AND " +
            "(LOWER(f.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<FiestaTradicion> searchActiveByKeywordPage(@Param("keyword") String keyword, Pageable pageable);

    // Metodo adicional: Calcular promedio de puntuación de una fiesta o tradición específica
    @Query("SELECT AVG(p.puntuacion) FROM Puntuacion p WHERE p.tipoEntidad = 'TRADICION' AND p.idEntidad = :idTradicion")
    Double findAveragePuntuacionByTradicionId(@Param("idTradicion") Long idTradicion);

}

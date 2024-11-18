package app.VilaExplorer.repository;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiestaTradicionRepository extends JpaRepository<FiestaTradicion, Long> {
    /**
     * Encuentra todas las fiestas tradicionales de un autor
     * @param autor
     */
    List<FiestaTradicion> findByAutor_IdUsuario(Usuario autor);

    /**
     * Encuentra todas las fiestas tradicionales de un autor activas
     * @param idAutor
     */
    @Query("SELECT f FROM FiestaTradicion f WHERE f.activo = true AND f.autor.idUsuario = :idAutor")
    List<FiestaTradicion> findActiveByAutor(@Param("idAutor") Long idAutor);



    /**
     * Busca fiestas tradicionales por palabra clave parcial o completa en el nombre o descripción
     * @param keyword
     */
    @Query("SELECT f FROM FiestaTradicion f WHERE LOWER(f.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<FiestaTradicion> searchByKeyword(@Param("keyword") String keyword);

    /**
     * Busca fiestas tradicionales por palabra clave parcial o completa en el nombre o descripción Paginado
     * paginado significa que se mostrara de a 10 elementos por pagina
     * @param keyword
     */
    @Query("SELECT f FROM FiestaTradicion f WHERE LOWER(f.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<FiestaTradicion> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);


    /**
     * Encuentra todas las fiestas tradicionales activas
     */
    @Query("SELECT f FROM FiestaTradicion f WHERE f.activo = true")
    List<FiestaTradicion> findAllActive();


    /**
     * Encuentra todas las fiestas tradicionales activas sin paginación
     */
    @Query("SELECT f FROM FiestaTradicion f WHERE f.activo = true AND " +
            "(LOWER(f.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<FiestaTradicion> searchActiveByKeyword(@Param("keyword") String keyword);


    /**
     * Encuentra todas las fiestas tradicionales activas con paginación
     */
    @Query("SELECT f FROM FiestaTradicion f WHERE f.activo = true AND " +
            "(LOWER(f.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<FiestaTradicion> searchActiveByKeyword(@Param("keyword") String keyword, Pageable pageable);

}

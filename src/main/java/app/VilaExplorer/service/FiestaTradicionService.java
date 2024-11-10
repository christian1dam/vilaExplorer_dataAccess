package app.VilaExplorer.service;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface FiestaTradicionService {
    Optional<FiestaTradicion> findById(Long id);
    List<FiestaTradicion> findAll();
    FiestaTradicion save(FiestaTradicion fiestaTradicion);
    void deleteById(Long id);
    List<FiestaTradicion> findByAutor(Usuario autor);
    // para buscar por palabra clave parcial o completa en el nombre o descripción
    List<FiestaTradicion> searchByKeyword(String keyword);
    // para buscar por palabra clave parcial o completa en el nombre o descripción paginado
    // paginado significa que se mostrara de a 10 elementos por pagina
    Page<FiestaTradicion> searchByKeyword(String keyword, Pageable pageable);

}

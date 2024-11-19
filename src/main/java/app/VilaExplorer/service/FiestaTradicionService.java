package app.VilaExplorer.service;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.exception.FiestaTradicionNotFound;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FiestaTradicionService {
    FiestaTradicion findById(Long id) throws FiestaTradicionNotFound;

    List<FiestaTradicion> findAll();

    FiestaTradicion save(FiestaTradicion fiestaTradicion, Long idAutor) throws UsuarioNotFoundException, DataIntegrityViolationException;

    void deleteById(Long id) throws FiestaTradicionNotFound;

    void deleteLogicallyById(Long id);

    List<FiestaTradicion> findByAutor(Usuario autor);

    // para buscar por palabra clave parcial o completa en el nombre o descripción
    List<FiestaTradicion> searchByKeyword(String keyword) throws FiestaTradicionNotFound;


    // para buscar por palabra clave parcial o completa en el nombre o descripción paginado
    // paginado significa que se mostrara de a 10 elementos por pagina
    Page<FiestaTradicion> searchByKeyword(String keyword, Pageable pageable) throws FiestaTradicionNotFound;

    List<FiestaTradicion> findAllActive();
    List<FiestaTradicion> searchActiveByKeyword(String keyword);
    Page<FiestaTradicion> searchActiveByKeyword(String keyword, Pageable pageable);
    List<FiestaTradicion> findActiveByAutor(Long idAutor);


    List<FiestaTradicion> getListaFiestasByAutor(Long idAutor) throws UsuarioNotFoundException;

    FiestaTradicion updateFiestaTradicion(Long id, FiestaTradicion fiestaTradicion) throws FiestaTradicionNotFound;
}

package app.VilaExplorer.service;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.repository.FiestaTradicionRepository;
import app.VilaExplorer.service.FiestaTradicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FiestaTradicionServiceImpl implements FiestaTradicionService {

    @Autowired
    private FiestaTradicionRepository fiestaTradicionRepository;

    /**
     * Encuentra todas las fiestas tradicionales de un autor
     * @param idAutor
     */
    @Override
    public Optional<FiestaTradicion> findById(Long idAutor) {
        return fiestaTradicionRepository.findById(idAutor);
    }

    /**
     * Encuentra todas las fiestas tradicionales
     * @return  Lista de fiestas tradicionales
     */
    @Override
    public List<FiestaTradicion> findAll() {
        return fiestaTradicionRepository.findAll();
    }

    /**
     * Guarda una fiesta tradicional
     * @param fiestaTradicion
     * @return  Fiesta tradicional guardada
     */
    @Override
    public FiestaTradicion save(FiestaTradicion fiestaTradicion) {
        return fiestaTradicionRepository.save(fiestaTradicion);
    }

    /**
     * Elimina una fiesta tradicional por su id
     * @param idfiestaTradicion
     */
    @Override
    public void deleteById(Long idfiestaTradicion) {
        fiestaTradicionRepository.deleteById(idfiestaTradicion);
    }

    /**
     * Encuentra todas las fiestas tradicionales de un autor
     * @param autor //objeto de tipo Usuario
     */
    @Override
    public List<FiestaTradicion> findByAutor(Usuario autor) {
        return fiestaTradicionRepository.findByAutorId(autor);
    }

    /**
     * Busca fiestas tradicionales por palabra clave parcial o completa en el nombre o descripción
     * @param keyword
     */
    @Override
    public List<FiestaTradicion> searchByKeyword(String keyword) {
        return fiestaTradicionRepository.searchByKeyword(keyword);
    }

    /**
     * Busca fiestas tradicionales por palabra clave parcial o completa en el nombre o descripción Paginado
     * paginado significa que se mostrara de a 10 elementos por pagina
     * @param keyword
     */
    @Override
    public Page<FiestaTradicion> searchByKeyword(String keyword, Pageable pageable) {
        return fiestaTradicionRepository.searchByKeyword(keyword, pageable);
    }

}
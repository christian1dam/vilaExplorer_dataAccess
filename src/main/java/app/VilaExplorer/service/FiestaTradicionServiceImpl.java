package app.VilaExplorer.service;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.exception.FiestaTradicionNotFound;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.repository.FiestaTradicionRepository;
import app.VilaExplorer.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiestaTradicionServiceImpl implements FiestaTradicionService {
    private static final String NOT_FOUND = "No existe una fiesta tradición con este ID en la base de datos";

    @Autowired
    private FiestaTradicionRepository fiestaTradicionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Encuentra todas las fiestas tradicionales de un autor
     * @param idFiestaTradicion
     */
    @Override
    public FiestaTradicion findById(Long idFiestaTradicion) throws FiestaTradicionNotFound {
        if (fiestaTradicionRepository.findById(idFiestaTradicion).isEmpty())
            throw new FiestaTradicionNotFound(NOT_FOUND);
        FiestaTradicion fiesta = fiestaTradicionRepository.findById(idFiestaTradicion).get();
        fiesta.setImagen(fiesta.getImagenBase64());
        return fiesta;
    }

    /**
     * Encuentra todas las fiestas tradicionales
     * @return  Lista de fiestas tradicionales
     */
    @Override
    public List<FiestaTradicion> findAll() {
        List<FiestaTradicion> fiestas = fiestaTradicionRepository.findAll();
        fiestas.forEach(FiestaTradicion::setImagenBase64FromPath); // Convertir la imagen a Base64
        return fiestas;
    }

    /**
     * Guarda una fiesta tradicional
     * @param fiestaTradicion
     * @return  Fiesta tradicional guardada
     */
    @Override
    @Transactional
    public FiestaTradicion save(FiestaTradicion fiestaTradicion, Long idAutor) throws UsuarioNotFoundException, DataIntegrityViolationException {
        if (usuarioRepository.findById(idAutor).isEmpty())
            throw new UsuarioNotFoundException("El usuario introducido no existe en la base de datos");
        if (!fiestaTradicionRepository.findByNombre(fiestaTradicion.getNombre()).isEmpty())
            throw new DataIntegrityViolationException("Esta fiesta tradición ya se encuentra en la base de datos");
        fiestaTradicion.setAutor(usuarioRepository.findById(idAutor).get());
        fiestaTradicionRepository.save(fiestaTradicion);
        return fiestaTradicion;
    }

    /**
     * Elimina una fiesta tradicional por su id
     * @param idfiestaTradicion
     */
    @Override
    public void deleteById(Long idfiestaTradicion) throws FiestaTradicionNotFound {
        if (fiestaTradicionRepository.findById(idfiestaTradicion).isEmpty())
            throw new FiestaTradicionNotFound(NOT_FOUND);
        fiestaTradicionRepository.deleteById(idfiestaTradicion);
    }


    @Override
    public void deleteLogicallyById(Long id) {
        FiestaTradicion fiesta = fiestaTradicionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FiestaTradicion no encontrada con ID: " + id));
        fiesta.setActivo(false); // Marcar como no activa
        fiestaTradicionRepository.save(fiesta); // Guardar los cambios
    }


    /**
     * Encuentra todas las fiestas tradicionales de un autor
     *
     * @param autor //objeto de tipo Usuario
     */
    @Override
    public List<FiestaTradicion> findByAutor(Usuario autor) {
        return fiestaTradicionRepository.findByAutor_IdUsuario(autor);
    }

    /**
     * Busca fiestas tradicionales por palabra clave parcial o completa en el nombre o descripción
     *
     * @param keyword
     */
    @Override
    public List<FiestaTradicion> searchByKeyword(String keyword) throws FiestaTradicionNotFound {
        if (fiestaTradicionRepository.searchByKeywordList(keyword).isEmpty())
            throw new FiestaTradicionNotFound("No existen fiestas con esta palabra clave");
        return fiestaTradicionRepository.searchByKeywordList(keyword);
    }

    /**
     * Busca fiestas tradicionales por palabra clave parcial o completa en el nombre o descripción Paginado
     * paginado significa que se mostrara de a 10 elementos por pagina
     * @param keyword
     */
    @Override
    public Page<FiestaTradicion> searchByKeyword(String keyword, Pageable pageable) throws FiestaTradicionNotFound {
        if (fiestaTradicionRepository.searchByKeywordPage(keyword, pageable).isEmpty())
            throw new FiestaTradicionNotFound("No existen fiestas con esta palabra clave");
        return fiestaTradicionRepository.searchByKeywordPage(keyword, pageable);
    }

    @Override
    public List<FiestaTradicion> findAllActive() {
        return List.of();
    }

    @Override
    public List<FiestaTradicion> searchActiveByKeyword(String keyword) {
        return List.of();
    }

    @Override
    public Page<FiestaTradicion> searchActiveByKeyword(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public List<FiestaTradicion> findActiveByAutor(Long idAutor) {
        return List.of();
    }

    @Override
    public List<FiestaTradicion> getListaFiestasByAutor(Long idAutor) throws UsuarioNotFoundException {
        if (usuarioRepository.findById(idAutor).isEmpty())
            throw new UsuarioNotFoundException("El usuario no existe en la base de datos");
        Usuario autor = usuarioRepository.findById(idAutor).get();
        return fiestaTradicionRepository.findByAutor_IdUsuario(autor);
    }

    @Override
    @Transactional
    public FiestaTradicion updateFiestaTradicion(Long id, FiestaTradicion fiestaTradicion) throws FiestaTradicionNotFound {
        if (fiestaTradicionRepository.findById(id).isEmpty()) throw new FiestaTradicionNotFound(NOT_FOUND);
        FiestaTradicion fiestaTradicionFromDB = fiestaTradicionRepository.findById(id).get();
        fiestaTradicionFromDB.setIdFiestaTradicion(fiestaTradicion.getIdFiestaTradicion());
        fiestaTradicionFromDB.setNombre(fiestaTradicion.getNombre());
        fiestaTradicionFromDB.setAutor(fiestaTradicion.getAutor());
        fiestaTradicionFromDB.setDescripcion(fiestaTradicion.getDescripcion());
        fiestaTradicionFromDB.setImagen(fiestaTradicion.getImagen());
        return fiestaTradicionRepository.save(fiestaTradicionFromDB);
    }

}
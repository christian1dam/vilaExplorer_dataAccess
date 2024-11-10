package app.VilaExplorer.service;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritoServiceImpl implements FavoritoService {
    @Autowired
    private FavoritoRepository favoritoRepository;

    /**
     * Devuelve un favorito por su id.
     * @param id el id del favorito
     * @return un Optional que puede contener un favorito
     */
    @Override
    public Optional<Favorito> findById(Long id) {
        return favoritoRepository.findById(id);
    }

    /**
     * Devuelve una lista de todos los favoritos.
     * @return una lista de favoritos
     */
    @Override
    public List<Favorito> findAll() {
        return favoritoRepository.findAll();
    }

    /**
     * Guarda un favorito.
     * @param favorito el favorito a guardar
     * @return el favorito guardado
     */
    @Override
    public Favorito save(Favorito favorito) {
        return favoritoRepository.save(favorito);
    }
    /**
     * Elimina un favorito por su id.
     * @param id el id del favorito
     */
    @Override
    public void deleteById(Long id) {
        favoritoRepository.deleteById(id);
    }

    /**
     * Devuelve una lista de favoritos de un usuario.
     * @param idUsuario el id del usuario
     * @return una lista de favoritos
     */
    @Override
    public List<Favorito> findByUsuario_IdUsuario(Long idUsuario) {
        return favoritoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    /**
     * Devuelve una lista de favoritos de un usuario para una entidad específica.
     * @param idUsuario el id del usuario
     * @param tipoEntidad el tipo de entidad
     * @return una lista de favoritos
     */
    @Override
    public List<Favorito> findByUsuario_IdUsuarioAndTipoEntidad(Long idUsuario, TipoEntidad tipoEntidad) {
        return favoritoRepository.findByUsuario_IdUsuarioAndTipoEntidad(idUsuario, tipoEntidad);
    }

}
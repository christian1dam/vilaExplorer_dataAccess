package app.VilaExplorer.service;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.exception.FavoritoNotFoundException;
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
     *
     * @param id el id del favorito
     * @return un Optional que puede contener un favorito
     */
    @Override
    public Favorito findById(Long id) throws FavoritoNotFoundException {
        if (favoritoRepository.findById(id).isEmpty())
            throw new FavoritoNotFoundException("El ID " + id + " no se encuentra en la base de datos");
        return favoritoRepository.findById(id).get();
    }

    /**
     * Devuelve una lista de todos los favoritos.
     *
     * @return una lista de favoritos
     */
    @Override
    public List<Favorito> findAll() {
        return favoritoRepository.findAll();
    }

    /**
     * Guarda un favorito.
     *
     * @param favorito el favorito a guardar
     * @return el favorito guardado
     */
    @Override
    public Favorito save(Favorito favorito) {
        return favoritoRepository.save(favorito);
    }

    /**
     * Elimina un favorito por su id.
     *
     * @param idEntidad el id de la entidad a la que representa el favorito (PLATO, LUGAR INTERES, FIESTA)
     * @param idUsuario el id de la entidad a la que representa el usuario
     */
    @Override
    public void deleteByIdEntidad(Long idEntidad, Long idUsuario) throws FavoritoNotFoundException {
        Optional<Favorito> favorito = favoritoRepository.findByIdEntidadAndUsuario_IdUsuario(idEntidad, idUsuario);
        if (favorito.isPresent()) {
            favoritoRepository.delete(favorito.get());
        } else {
            throw new FavoritoNotFoundException("No se encontró el favorito con idEntidad: " + idEntidad + " y idUsuario: " + idUsuario);
        }
    }

    /**
     * Devuelve una lista de favoritos de un usuario.
     *
     * @param idUsuario el id del usuario
     * @return una lista de favoritos
     */
    @Override
    public List<Favorito> findByUsuario_IdUsuario(Long idUsuario) {
        return favoritoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    /**
     * Devuelve una lista de favoritos de un usuario para una entidad específica.
     *
     * @param idUsuario   el id del usuario
     * @param tipoEntidad el tipo de entidad
     * @return una lista de favoritos
     */
    @Override
    public List<Favorito> findByUsuario_IdUsuarioAndTipoEntidad(Long idUsuario, TipoEntidad tipoEntidad) {
        return favoritoRepository.findByUsuario_IdUsuarioAndTipoEntidad(idUsuario, tipoEntidad);
    }

    @Override
    public Favorito updateFavorito(Long id, Favorito favorito) throws FavoritoNotFoundException {
        if (favoritoRepository.findById(id).isEmpty())
            throw new FavoritoNotFoundException("El ID " + id + " no existe en la base de datos");

        Favorito favoritoFromDB = favoritoRepository.findById(id).get();
        favoritoFromDB.setIdFavorito(favorito.getIdFavorito());
        favoritoFromDB.setUsuario(favorito.getUsuario());
        favoritoFromDB.setIdEntidad(favorito.getIdEntidad());
        favoritoFromDB.setTipoEntidad(favorito.getTipoEntidad());

        return favoritoRepository.save(favoritoFromDB);
    }

}
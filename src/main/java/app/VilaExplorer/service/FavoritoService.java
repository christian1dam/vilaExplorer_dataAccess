package app.VilaExplorer.service;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.exception.FavoritoNotFoundException;

import java.util.List;

public interface FavoritoService {
    Favorito findById(Long id) throws FavoritoNotFoundException;

    List<Favorito> findAll();

    Favorito save(Favorito favorito);

    void deleteById(Long id) throws FavoritoNotFoundException;

    List<Favorito> findByUsuario_IdUsuario(Long idUsuario);

    /**
     * Devuelve una lista de favoritos de un usuario para una entidad específica.
     *
     * @param idUsuario   el id del usuario
     * @param tipoEntidad el tipo de entidad
     * @return una lista de favoritos
     */
    List<Favorito> findByUsuario_IdUsuarioAndTipoEntidad(Long idUsuario, TipoEntidad tipoEntidad);

    Favorito updateFavorito(Long id, Favorito favorito) throws FavoritoNotFoundException;
}
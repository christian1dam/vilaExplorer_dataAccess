package app.VilaExplorer.service;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.enums.TipoEntidad;

import java.util.List;
import java.util.Optional;

public interface FavoritoService {
    Optional<Favorito> findById(Long id);
    List<Favorito> findAll();
    Favorito save(Favorito favorito);
    void deleteById(Long id);
    List<Favorito> findByUsuarioId(Long idUsuario);

    /**
     * Devuelve una lista de favoritos de un usuario para una entidad específica.
     * @param idUsuario el id del usuario
     * @param tipoEntidad el tipo de entidad
     * @return una lista de favoritos
     */
    List<Favorito> findByUsuarioIdAndTipoEntidad(Long idUsuario, TipoEntidad tipoEntidad);
}
package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.enums.TipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuario_IdUsuario(Long idUsuario);
    List<Favorito> findByUsuario_IdUsuarioAndTipoEntidad(Long idUsuario, TipoEntidad tipoEntidad);
    Optional<Favorito> findByIdEntidadAndUsuario_IdUsuario(Long idEntidad, Long idUsuario);
}

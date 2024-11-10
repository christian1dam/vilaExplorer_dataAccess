package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.enums.TipoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuarioId(Long idUsuario);
    List<Favorito> findByUsuarioIdAndTipoEntidad(Long idUsuario, TipoEntidad tipoEntidad);
}

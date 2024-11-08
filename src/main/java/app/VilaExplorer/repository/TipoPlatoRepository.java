package app.VilaExplorer.repository;

import app.VilaExplorer.domain.TipoPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPlatoRepository extends JpaRepository<TipoPlato, Long> {
}

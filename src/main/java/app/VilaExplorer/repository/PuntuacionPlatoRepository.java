package app.VilaExplorer.repository;

import app.VilaExplorer.domain.PuntuacionPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntuacionPlatoRepository extends JpaRepository<PuntuacionPlato, Long> {
}

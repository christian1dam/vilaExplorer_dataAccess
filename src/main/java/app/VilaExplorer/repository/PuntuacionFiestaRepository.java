package app.VilaExplorer.repository;

import app.VilaExplorer.domain.PuntuacionFiesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntuacionFiestaRepository extends JpaRepository<PuntuacionFiesta, Long> {
}

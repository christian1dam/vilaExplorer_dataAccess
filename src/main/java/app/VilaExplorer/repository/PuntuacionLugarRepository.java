package app.VilaExplorer.repository;

import app.VilaExplorer.domain.PuntuacionLugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntuacionLugarRepository extends JpaRepository<PuntuacionLugar, Long> {
}

package app.VilaExplorer.repository;

import app.VilaExplorer.domain.RutaLugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaLugarRepository extends JpaRepository<RutaLugar, Long> {
}

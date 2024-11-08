package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Coordenadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadasRepository extends JpaRepository<Coordenadas, Long> {
}

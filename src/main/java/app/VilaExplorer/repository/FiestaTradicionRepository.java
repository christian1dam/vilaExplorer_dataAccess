package app.VilaExplorer.repository;

import app.VilaExplorer.domain.FiestaTradicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiestaTradicionRepository extends JpaRepository<FiestaTradicion, Long> {
}

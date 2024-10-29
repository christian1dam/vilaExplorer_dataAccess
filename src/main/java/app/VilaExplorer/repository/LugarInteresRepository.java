package app.VilaExplorer.repository;

import app.VilaExplorer.domain.LugarInteres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LugarInteresRepository extends JpaRepository<LugarInteres, Long> {
}
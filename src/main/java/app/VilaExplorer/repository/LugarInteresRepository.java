package app.VilaExplorer.repository;

import app.VilaExplorer.domain.LugarInteres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LugarInteresRepository extends JpaRepository<LugarInteres, Long> {

    List<LugarInteres> findAllByActivoTrue();
}
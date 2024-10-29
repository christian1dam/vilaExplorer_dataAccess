package app.VilaExplorer.repository;

import app.VilaExplorer.domain.TipoLugarInteres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoLugarInteresRepository extends JpaRepository<TipoLugarInteres, Long> {
}
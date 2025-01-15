package app.VilaExplorer.repository;

import app.VilaExplorer.domain.TipoLugarInteres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoLugarInteresRepository extends JpaRepository<TipoLugarInteres, Long> {

    // Nuevo método para obtener solo los registros que estén activos
    List<TipoLugarInteres> findByActivoTrue();

}
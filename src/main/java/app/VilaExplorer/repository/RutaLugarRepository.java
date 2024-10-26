package app.VilaExplorer.repository;

import app.VilaExplorer.domain.RutaLugar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RutaLugarRepository extends JpaRepository<RutaLugar, Long> {
    // Puedes agregar consultas personalizadas si es necesario
}

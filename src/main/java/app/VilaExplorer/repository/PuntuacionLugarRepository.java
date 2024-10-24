package app.VilaExplorer.repository;

import app.VilaExplorer.domain.PuntuacionLugar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuntuacionLugarRepository extends JpaRepository<PuntuacionLugar, Long> {
    // Puedes agregar consultas personalizadas si es necesario
}

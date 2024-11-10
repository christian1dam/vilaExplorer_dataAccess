package app.VilaExplorer.repository;


import app.VilaExplorer.domain.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    // Encuentra rutas por autor
    List<Ruta> findByAutorId(Long autorId);
}
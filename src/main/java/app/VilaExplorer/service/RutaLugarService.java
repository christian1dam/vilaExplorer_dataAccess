package app.VilaExplorer.service;

import app.VilaExplorer.domain.RutaLugar;

import java.util.List;
import java.util.Optional;

public interface RutaLugarService {
    List<RutaLugar> findAll();
    Optional<RutaLugar> findById(Long id);
    RutaLugar save(RutaLugar rutaLugar);
    void deleteById(Long id);
    // Métodos adicionales si los necesitas
}

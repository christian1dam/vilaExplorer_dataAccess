package app.VilaExplorer.service;

import app.VilaExplorer.domain.PuntuacionLugar;

import java.util.List;
import java.util.Optional;

public interface PuntuacionLugarService {
    List<PuntuacionLugar> findAll();
    Optional<PuntuacionLugar> findById(Long id);
    PuntuacionLugar save(PuntuacionLugar puntuacionLugar);
    void deleteById(Long id);
    // Métodos adicionales si los necesitas
}

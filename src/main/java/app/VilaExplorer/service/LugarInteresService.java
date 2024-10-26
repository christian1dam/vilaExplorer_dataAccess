package app.VilaExplorer.service;

import app.VilaExplorer.domain.LugarInteres;

import java.util.List;
import java.util.Optional;

public interface LugarInteresService {
    List<LugarInteres> findAll();
    Optional<LugarInteres> findById(Long id);
    LugarInteres save(LugarInteres lugarInteres);
    void deleteById(Long id);
    // Métodos adicionales si los necesitas
}


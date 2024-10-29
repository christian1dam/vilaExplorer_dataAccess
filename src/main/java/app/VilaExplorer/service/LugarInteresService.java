package app.VilaExplorer.service;

import app.VilaExplorer.domain.LugarInteres;

import java.util.List;
import java.util.Optional;

public interface LugarInteresService {
    Optional<LugarInteres> findById(Long id);
    List<LugarInteres> findAll();
    LugarInteres save(LugarInteres lugarInteres);
    void deleteById(Long id);
}


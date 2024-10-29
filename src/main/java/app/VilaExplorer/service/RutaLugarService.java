package app.VilaExplorer.service;

import app.VilaExplorer.domain.RutaLugar;

import java.util.List;
import java.util.Optional;


public interface RutaLugarService {
    Optional<RutaLugar> findById(Long id);
    List<RutaLugar> findAll();
    RutaLugar save(RutaLugar rutaLugar);
    void deleteById(Long id);
}


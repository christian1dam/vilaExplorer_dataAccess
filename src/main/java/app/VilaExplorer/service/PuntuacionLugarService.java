package app.VilaExplorer.service;

import app.VilaExplorer.domain.PuntuacionLugar;

import java.util.List;
import java.util.Optional;


public interface PuntuacionLugarService {
    Optional<PuntuacionLugar> findById(Long id);
    List<PuntuacionLugar> findAll();
    PuntuacionLugar save(PuntuacionLugar puntuacionLugar);
    void deleteById(Long id);
}
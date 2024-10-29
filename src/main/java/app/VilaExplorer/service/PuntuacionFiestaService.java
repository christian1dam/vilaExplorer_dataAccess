package app.VilaExplorer.service;


import app.VilaExplorer.domain.PuntuacionFiesta;

import java.util.List;
import java.util.Optional;

public interface PuntuacionFiestaService {
    Optional<PuntuacionFiesta> findById(Long id);
    List<PuntuacionFiesta> findAll();
    PuntuacionFiesta save(PuntuacionFiesta puntuacionFiesta);
    void deleteById(Long id);
}
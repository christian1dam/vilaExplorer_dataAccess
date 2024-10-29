package app.VilaExplorer.service;


import app.VilaExplorer.domain.PuntuacionPlato;

import java.util.List;
import java.util.Optional;

public interface PuntuacionPlatoService {
    Optional<PuntuacionPlato> findById(Long id);
    List<PuntuacionPlato> findAll();
    PuntuacionPlato save(PuntuacionPlato puntuacionPlato);
    void deleteById(Long id);
}
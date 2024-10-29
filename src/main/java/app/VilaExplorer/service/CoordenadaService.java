package app.VilaExplorer.service;

import app.VilaExplorer.domain.Coordenada;

import java.util.List;
import java.util.Optional;

public interface CoordenadaService {
    Optional<Coordenada> findById(Long id);
    List<Coordenada> findAll();
    Coordenada save(Coordenada coordenada);
    void deleteById(Long id);
}
package app.VilaExplorer.service;

import app.VilaExplorer.domain.Coordenadas;

import java.util.List;
import java.util.Optional;

public interface CoordenadasService {
    Optional<Coordenadas> findById(Long id);
    List<Coordenadas> findAll();
    Coordenadas save(Coordenadas coordenadas);
    void deleteById(Long id);
}

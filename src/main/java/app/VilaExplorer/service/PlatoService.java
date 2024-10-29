package app.VilaExplorer.service;

import app.VilaExplorer.domain.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
    Optional<Plato> findById(Long id);
    List<Plato> findAll();
    Plato save(Plato plato);
    void deleteById(Long id);
}
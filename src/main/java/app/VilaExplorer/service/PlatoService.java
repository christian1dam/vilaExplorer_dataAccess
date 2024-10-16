package app.VilaExplorer.service;

import app.VilaExplorer.domain.Plato;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
    List<Plato> findAll();

    Optional<Plato> findById(Long id);

    Plato addPlato(Plato plato);
}
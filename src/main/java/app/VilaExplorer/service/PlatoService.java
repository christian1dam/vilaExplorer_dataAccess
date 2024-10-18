package app.VilaExplorer.service;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.exception.PlatoNotFoundException;

import java.util.List;
import java.util.Optional;

public interface PlatoService {
    List<Plato> findAll();

    Optional<Plato> findById(Long id);

    Plato addPlato(Plato plato);

    Plato actualizarPlato(Long id, Plato platoUpdated) throws PlatoNotFoundException;
}
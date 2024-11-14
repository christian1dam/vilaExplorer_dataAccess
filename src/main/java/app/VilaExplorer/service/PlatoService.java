package app.VilaExplorer.service;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface PlatoService {
    Plato findById(Long id) throws PlatoNotFoundException;

    List<Plato> findAll() throws PlatoNotFoundException;

    Plato save(Plato plato);

    void deleteById(Long id) throws PlatoNotFoundException;

    //metodo para aprobar un plato
    Plato aprobarPlato(Long platoId, Long aprobadorId) throws PlatoNotFoundException, UsuarioNotFoundException, RolNotFoundException;

    Plato createPlato(Plato plato) throws DataIntegrityViolationException;

    Plato updatePlato(Long id, Plato platoDetalles) throws PlatoNotFoundException;
}
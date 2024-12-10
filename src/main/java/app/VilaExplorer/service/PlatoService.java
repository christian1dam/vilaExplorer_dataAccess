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

    //metodo para crear un plato
    Plato createPlato(Plato plato) throws DataIntegrityViolationException;

    //metodo para actualizar un plato
    Plato updatePlato(Long id, Plato platoDetalles) throws PlatoNotFoundException;

    //metodo para actualizar la puntuacion media de un plato
    void actualizarPuntuacionMediaPlato(Long platoId) throws PlatoNotFoundException;

    //metodo para borrar logicamente un plato
    void borrarLogico(Long platoId) throws PlatoNotFoundException;

    //metodo para encontrar platos aprobados y no eliminados
    List<Plato> findAprobadosNoEliminados();

    //metodo para encontrar platos no aprobados
    List<Plato> findNoAprobados();

    //metodo para encontrar platos eliminados
    List<Plato> findEliminados();

    //metodo para encontrar platos no aprobados y no eliminados
    List<Plato> findNoAprobadosNoEliminados();






}
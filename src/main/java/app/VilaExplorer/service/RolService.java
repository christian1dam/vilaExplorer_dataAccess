package app.VilaExplorer.service;

import app.VilaExplorer.domain.Rol;
import app.VilaExplorer.exception.RolNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface RolService {
    List<Rol> getAll();
    List<Rol> getAllActivos(); // Obtener todos los roles activos
    void eliminarRolPorID(Long id) throws RolNotFoundException;
    Rol anyadirRol(Rol rol) throws DataIntegrityViolationException;
    Rol getRolByNombre(String rol) throws RolNotFoundException;
    void desactivaRolPorID(Long id) throws RolNotFoundException;
}

package app.VilaExplorer.service;

import app.VilaExplorer.domain.Rol;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    RolRepository rolRepository;


    @Override
    public List<Rol> getAll() {
        return rolRepository.findAll();
    }

    @Override
    public void eliminarRolPorID(Long id) throws RolNotFoundException {
        if (!rolRepository.existsById(id)) {
            throw new RolNotFoundException("El rol con el ID: " + id + " no se ha encontrado en la base de datos");
        } else rolRepository.deleteById(id);
    }

    @Override
    public Rol anyadirRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public Optional<Rol> getRolByNombre(String rol) {
        return rolRepository.findByNombre(rol);
    }

    // Metodo para obtener todos los roles activos
    @Override
    public List<Rol> getAllActivos() {
        return rolRepository.findByActivoTrue(); // Solo roles activos
    }
}

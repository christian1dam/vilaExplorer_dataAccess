package app.VilaExplorer.service;

import app.VilaExplorer.domain.Rol;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Rol anyadirRol(Rol rol) throws DataIntegrityViolationException {
        if(rolRepository.findByNombre(rol.getNombre()).isPresent()){
            throw new DataIntegrityViolationException("Este rol ya existe en la base de datos");
        }
        return rolRepository.save(rol);
    }

    @Override
    public Rol getRolByNombre(String rol) throws RolNotFoundException {
        if (rolRepository.findByNombre(rol).isEmpty()){
            throw new RolNotFoundException("Este rol no existe en la base de datos.");
        }
        return rolRepository.findByNombre(rol).get();
    }

    @Override
    public void desactivaRolPorID(Long id) throws RolNotFoundException {
        if(rolRepository.findById(id).isEmpty()) throw new RolNotFoundException("El id que has introducido no pertenece a ningún rol");
        rolRepository.findById(id).get().setActivo(false);
    }

    // Metodo para obtener todos los roles activos
    @Override
    public List<Rol> getAllActivos() {
        return rolRepository.findByActivoTrue(); // Solo roles activos
    }
}

package app.VilaExplorer.service;

import app.VilaExplorer.domain.Rol;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.domain.UsuarioRol;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.repository.RolRepository;
import app.VilaExplorer.repository.UsuarioRepository;
import app.VilaExplorer.repository.UsuarioRolRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(Long id) throws RolNotFoundException {
        if (!usuarioRepository.existsById(id)) {
            throw new RolNotFoundException("El usuario con el ID " + id + " no existe en la base de datos");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return usuarioRepository.existsById(id);
    }

    @Override
    @Transactional
    public Usuario updateRolDelUsuario(Long usuarioId, String nombreRol) throws UsuarioNotFoundException, RolNotFoundException {
        // Buscar el usuario por ID
        if (usuarioRepository.findById(usuarioId).isEmpty()) {
            throw new UsuarioNotFoundException("Usuario no encontrado en la base de datos");
        }
        if (rolRepository.findByNombre(nombreRol).isEmpty()) {
            throw new RolNotFoundException("Rol no encontrado");
        }
        Usuario usuarioFromDB = usuarioRepository.findById(usuarioId).get();
        return asignarRolAUsuario(usuarioFromDB, nombreRol);
    }

    @Override
    public List<Usuario> findUsuariosByRol(String rol) throws RolNotFoundException {
        if (rolRepository.findByNombre(rol).isEmpty()) {
            throw new RolNotFoundException("El rol no existe en la base de datos");
        }
        return usuarioRepository.findUsuariosByRol(rol);
    }

    @Override
    @Transactional
    public Usuario crearUsuarioConRol(Usuario usuario, String rol) throws RolNotFoundException, DataIntegrityViolationException {
        /*
        Primero se hacen validaciones antes de crear el usuario,
        verificando si ya existe un usuario con el mismo correo electronico y
        si el rol que se le quiere asignar no existe en la base de datos.
         */

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new DataIntegrityViolationException("Este email ya existe en la base de datos.");
        } else if (rolRepository.findByNombre(rol).isEmpty()) {
            throw new RolNotFoundException("No se ha encontrado el rol en la base de datos");
        }
        return asignarRolAUsuario(usuario, rol);
    }

    @Override
    @Transactional
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) throws UsuarioNotFoundException {
        if (usuarioRepository.findById(id).isEmpty()) {
            throw new UsuarioNotFoundException("Error, no existe un usuario con este ID: " + id);
        }
        Usuario usuarioActualizado = usuarioRepository.findById(id).get();
        usuarioActualizado.setNombre(usuarioDetails.getNombre());
        usuarioActualizado.setEmail(usuarioDetails.getEmail());
        usuarioActualizado.setPassword(usuarioDetails.getPassword());
        usuarioActualizado.setEstado(usuarioDetails.getEstado());
        usuarioActualizado.setFechaCreacion(usuarioDetails.getFechaCreacion());
        return usuarioRepository.save(usuarioActualizado);
    }

    private Usuario asignarRolAUsuario(Usuario usuario, String rol) {
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        assert rolRepository.findByNombre(rol).isPresent();
        Rol rolFromDB = rolRepository.findByNombre(rol).get();

        // Asigna el rol después de guardar el usuario
        usuarioGuardado.setRolActual(rolFromDB);

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuarioGuardado);
        usuarioRol.setRol(rolFromDB);
        usuarioRol.setFechaDeAsignacion(LocalDateTime.now());

        usuarioRolRepository.save(usuarioRol);

        usuarioGuardado.getRoles().add(usuarioRol);

        return usuarioRepository.save(usuarioGuardado);
    }
}
package app.VilaExplorer.service;

import app.VilaExplorer.domain.Rol;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.domain.UsuarioRol;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.repository.RolRepository;
import app.VilaExplorer.repository.UsuarioRepository;
import app.VilaExplorer.repository.UsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    public Usuario findById(Long id) throws UsuarioNotFoundException {
        if (usuarioRepository.findById(id).isEmpty())
            throw new UsuarioNotFoundException("El usuario con el ID " + id + " no existe en la base de datos");
        return usuarioRepository.findById(id).get();
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteById(Long id) throws UsuarioNotFoundException {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException("El usuario con el ID " + id + " no existe en la base de datos");
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Usuario editarNombre(Long id, String nuevoNombre) throws UsuarioNotFoundException {
        if (usuarioRepository.findById(id).isEmpty()) {
            throw new UsuarioNotFoundException("El usuario con ID " + id + " no existe.");
        }
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setNombre(nuevoNombre);
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario editarContrasenya(Long id, String contrasenyaActual, String nuevaContrasenya) throws UsuarioNotFoundException {
        if (usuarioRepository.findById(id).isEmpty()) {
            throw new UsuarioNotFoundException("El usuario con ID " + id + " no existe.");
        }
        Usuario usuario = usuarioRepository.findById(id).get();

        if (!usuario.getPassword().equals(contrasenyaActual)) {
            throw new IllegalArgumentException("La contraseña actual no es válida.");
        }

        usuario.setPassword(nuevaContrasenya);
        return usuarioRepository.save(usuario);
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
        usuarioActualizado.setActivo(usuarioDetails.getActivo());
        usuarioActualizado.setFechaCreacion(usuarioDetails.getFechaCreacion());
        return usuarioRepository.save(usuarioActualizado);
    }

    @Override
    public void deleteUsuarioLogico(Long id) throws UsuarioNotFoundException {
        if (usuarioRepository.findById(id).isEmpty())
            throw new UsuarioNotFoundException("El usuario no se encuentra en la base de datos.");
        Usuario usuarioExistente = usuarioRepository.findById(id).get();
        usuarioExistente.setActivo(false);
        usuarioRepository.save(usuarioExistente);
    }

    @Override
    public Usuario findUser(String email, String password) throws UsuarioNotFoundException {
        if (usuarioRepository.findByEmailAndPassword(email, password).isEmpty())
            throw new UsuarioNotFoundException("Este usuario no existe en la base de datos");
        return usuarioRepository.findByEmailAndPassword(email, password).get();
    }

    //    Solamente se llama a este metodo despues de haber hecho las validaciones de usuario y rol.
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
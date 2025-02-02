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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
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

    @Autowired
    private PasswordEncoder encoder;

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

        if (usuarioDetails.getNombre() != null) {
            usuarioActualizado.setNombre(usuarioDetails.getNombre());
        }

        if (usuarioDetails.getEmail() != null) {
            usuarioActualizado.setEmail(usuarioDetails.getEmail());
        }

        if (usuarioDetails.getPassword() != null) {
            usuarioActualizado.setPassword(encoder.encode(usuarioDetails.getPassword()));
            System.out.println("SE HA ACTUALIZADO LA CONTRASEÑA");
        }

        if (usuarioDetails.getActivo() != null) {
            usuarioActualizado.setActivo(usuarioDetails.getActivo());
        }

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

    @Override
    public Boolean validatePassword(Usuario usuario) throws UsuarioNotFoundException {
        if(usuarioRepository.findById(usuario.getIdUsuario()).isEmpty())
            throw new UsuarioNotFoundException("Este usuario no existe en la base de datos");
        Usuario usuarioDB = usuarioRepository.findById(usuario.getIdUsuario()).get();
        return encoder.matches(usuario.getPassword(), usuarioDB.getPassword());
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
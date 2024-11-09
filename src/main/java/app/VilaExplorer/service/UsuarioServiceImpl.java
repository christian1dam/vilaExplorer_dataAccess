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
    public Usuario asignarRolAUsuario(Long usuarioId, String nombreRol) throws UsuarioNotFoundException, RolNotFoundException {
        // Buscar el usuario por ID
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        // Buscar el rol por nombre
        Rol rol = rolRepository.findByNombre(nombreRol)
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado"));

        // Asignar el nuevo rol como rol actual del usuario
        usuario.setRolActual(rol);

        // Crear la relación UsuarioRol con la fecha de asignación
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        usuarioRol.setFechaDeAsignacion(LocalDateTime.now());

        // Guardar en el historial de roles
        usuarioRolRepository.save(usuarioRol);

        // Agregar el nuevo UsuarioRol a la lista roles del usuario en memoria
        usuario.getRoles().add(usuarioRol);

        // Guardar el usuario con el nuevo rol actual
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> findUsuariosByRol(String rol) throws RolNotFoundException {
        Rol rolFromDB = rolRepository.findByNombre(rol)
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado"));
        return usuarioRepository.findUsuariosByRol(rol);
    }

    @Override
    @Transactional
    public Usuario crearUsuarioConRol(Usuario usuario, String rol) throws RolNotFoundException, UsuarioNotFoundException {
        // Guardar el usuario sin rol
        Usuario newUsuario = usuarioRepository.save(usuario);

        // Usar asignarRolAUsuario para asignar el rol al usuario en una transacción
        return asignarRolAUsuario(newUsuario.getIdUsuario(), rol);
    }
}

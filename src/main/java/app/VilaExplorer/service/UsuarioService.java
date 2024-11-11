package app.VilaExplorer.service;

import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Usuario save(Usuario usuario);

    void deleteById(Long id) throws RolNotFoundException;

    boolean existsById(Long id) throws RolNotFoundException;

    Usuario updateRolDelUsuario(Long usuarioId, String rol) throws UsuarioNotFoundException, RolNotFoundException;

    List<Usuario> findUsuariosByRol(String rol) throws RolNotFoundException;

    Usuario crearUsuarioConRol(Usuario usuario, String rol) throws RolNotFoundException, DataIntegrityViolationException;

    Usuario updateUsuario(Long id, Usuario usuarioDetails) throws UsuarioNotFoundException;
}

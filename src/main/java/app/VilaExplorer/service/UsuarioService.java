package app.VilaExplorer.service;

import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface UsuarioService {
    List<Usuario> findAll();

    Usuario findById(Long id) throws UsuarioNotFoundException;

    Usuario save(Usuario usuario);

    void deleteById(Long id) throws UsuarioNotFoundException;

    Usuario updateRolDelUsuario(Long usuarioId, String rol) throws UsuarioNotFoundException, RolNotFoundException;

    List<Usuario> findUsuariosByRol(String rol) throws RolNotFoundException;

    Usuario crearUsuarioConRol(Usuario usuario, String rol) throws RolNotFoundException, DataIntegrityViolationException;

    Usuario updateUsuario(Long id, Usuario usuarioDetails) throws UsuarioNotFoundException;

    void deleteUsuarioLogico(Long id) throws UsuarioNotFoundException;

    Usuario findUser(String email, String password) throws UsuarioNotFoundException;
}

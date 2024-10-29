package app.VilaExplorer.service;

import app.VilaExplorer.domain.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> findById(Long id);
    List<Usuario> findAll();
    Usuario save(Usuario usuario);
    void deleteById(Long id);
}
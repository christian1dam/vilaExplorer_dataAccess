package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar un usuario por su nombre de usuario
    @Query("SELECT u FROM Usuario u JOIN u.roles ur JOIN ur.rol r WHERE r.nombre = :rol")
    List<Usuario> findUsuariosByRol(@Param("rol") String rol);

    Optional<Usuario> findByEmail(String email);
}

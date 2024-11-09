package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u JOIN u.roles ur JOIN ur.rol r WHERE r.nombre = :rol")
    List<Usuario> findUsuariosByRol(@Param("rol") String rol);
}

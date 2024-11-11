package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);

    // Metodo para buscar roles activos
    List<Rol> findByActivoTrue();

    // Metodo para buscar por ID y asegurarse de que el rol esté activo
    Optional<Rol> findByIdRolAndActivoTrue(Long idRol);
}

package app.VilaExplorer.repository;


import app.VilaExplorer.domain.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    // Encuentra rutas por autor
    List<Ruta> findByAutor_IdUsuario(Long autorId);//JPa entederá que el campo autor_id_usuario es el que se debe buscar

    // Encuentra rutas activas
    List<Ruta> findAllByActivoTrue();

    // Metodo para obtener ruts que no sean predefinidas
    Optional<Ruta> findByIdRutaAndPredefinidaFalse(Long idRuta);

    // Metodo para obtener rutas que sean predefinidas
    List<Ruta> findByPredefinidaTrue();

    // Encuentra todas las rutas inactivas
    List<Ruta> findAllByActivoFalse();



}
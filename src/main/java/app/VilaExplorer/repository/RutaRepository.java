package app.VilaExplorer.repository;


import app.VilaExplorer.domain.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {
    // Encuentra rutas por autor
    List<Ruta> findByAutor_IdUsuario(Long autorId);//JPa entederá que el campo autor_id_usuario es el que se debe buscar

    // Encuentra rutas activas
    List<Ruta> findAllByActivoTrue();
}
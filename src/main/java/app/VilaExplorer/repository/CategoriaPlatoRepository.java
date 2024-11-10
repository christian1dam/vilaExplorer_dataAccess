package app.VilaExplorer.repository;


import app.VilaExplorer.domain.CategoriaPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaPlatoRepository extends JpaRepository<CategoriaPlato, Long> {
    // Metodo para  buscar todas las categorías activas
    List<CategoriaPlato> findByActivoTrue();
}
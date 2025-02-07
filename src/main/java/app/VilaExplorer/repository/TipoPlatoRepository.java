package app.VilaExplorer.repository;

import app.VilaExplorer.domain.TipoPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoPlatoRepository extends JpaRepository<TipoPlato, Long> {
    // Obtener todos los tipos de plato activos
    List<TipoPlato> findByActivoTrue();

    // Obtener todos los tipos de plato, incluidos los inactivos
    List<TipoPlato> findAll();

    // Buscar un tipo de plato por su ID y que esté activo
    TipoPlato findByIdTipoPlatoAndActivoTrue(Long idTipoPlato);

    // Obtener todos los tipos de plato inactivos
    List<TipoPlato> findByActivoFalse();

    // Buscar todos los tipos de plato activos por ID de categoría
    List<TipoPlato> findByCategoriaPlato_IdCategoriaPlatoAndActivoTrue(Long idCategoriaPlato);


}

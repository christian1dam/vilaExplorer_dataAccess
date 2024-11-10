package app.VilaExplorer.service;

import app.VilaExplorer.domain.TipoPlato;

import java.util.List;
import java.util.Optional;

public interface TipoPlatoService {
    // Obtener todos los tipos de plato
    List<TipoPlato> findAll();

    // Obtener un tipo de plato por ID
    Optional<TipoPlato> findById(Long id);

    List<TipoPlato> findAllActivos();

    // Guardar o actualizar un tipo de plato
    TipoPlato save(TipoPlato tipoPlato);

    // Borrado lógico de un tipo de plato
    void deleteByIdLogico(Long id);

    // Obtener todos los tipos de plato activos por ID de categoría
    List<TipoPlato> findByCategoriaId(Long categoriaId);


}

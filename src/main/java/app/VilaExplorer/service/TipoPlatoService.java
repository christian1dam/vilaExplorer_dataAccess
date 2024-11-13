package app.VilaExplorer.service;

import app.VilaExplorer.domain.TipoPlato;
import app.VilaExplorer.exception.TipoPlatoNotFoundException;

import java.util.List;

public interface TipoPlatoService {
    // Obtener todos los tipos de plato
    List<TipoPlato> findAll() throws TipoPlatoNotFoundException;

    // Obtener un tipo de plato por ID
    TipoPlato findById(Long id) throws TipoPlatoNotFoundException;

    List<TipoPlato> findAllActivos() throws TipoPlatoNotFoundException;

    // Guardar o actualizar un tipo de plato
    TipoPlato save(TipoPlato tipoPlato);

    // Borrado lógico de un tipo de plato
    void deleteByIdLogico(Long id) throws TipoPlatoNotFoundException;

    // Obtener todos los tipos de plato activos por ID de categoría
    List<TipoPlato> findByCategoriaId(Long categoriaId);
}

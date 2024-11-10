package app.VilaExplorer.service;


import app.VilaExplorer.domain.CategoriaPlato;

import java.util.List;
import java.util.Optional;

public interface CategoriaPlatoService {
    Optional<CategoriaPlato> findById(Long id);
    List<CategoriaPlato> findAll();
    List<CategoriaPlato> findAllActivos(); // Metodo para obtener solo las categorías activas
    CategoriaPlato save(CategoriaPlato categoriaPlato);
    void deleteByIdLogico(Long id); // Cambiado para realizar un borrado lógico
}

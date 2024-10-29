package app.VilaExplorer.service;


import app.VilaExplorer.domain.CategoriaPlato;

import java.util.List;
import java.util.Optional;

public interface CategoriaPlatoService {
    Optional<CategoriaPlato> findById(Long id);
    List<CategoriaPlato> findAll();
    CategoriaPlato save(CategoriaPlato categoriaPlato);
    void deleteById(Long id);
}

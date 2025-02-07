package app.VilaExplorer.service;


import app.VilaExplorer.domain.CategoriaPlato;
import app.VilaExplorer.exception.CategoriaPlatoNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface CategoriaPlatoService {
    CategoriaPlato findById(Long id) throws CategoriaPlatoNotFoundException;
    List<CategoriaPlato> findAll();
    List<CategoriaPlato> findAllActivos(); // Metodo para obtener solo las categorías activas
    List<CategoriaPlato> findAllInactivas(); // Obtener solo inactivas
    CategoriaPlato crearCategoriaPlato(CategoriaPlato categoriaPlato) throws DataIntegrityViolationException;
    void deleteByIdLogico(Long id) throws CategoriaPlatoNotFoundException; // Cambiado para realizar un borrado lógico
    CategoriaPlato updateCategoriaPlato(Long id, CategoriaPlato categoriaPlato) throws CategoriaPlatoNotFoundException;
}

package app.VilaExplorer.service;


import app.VilaExplorer.domain.Articulo;
import app.VilaExplorer.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface ArticuloService {
    Optional<Articulo> findById(Long id);
    List<Articulo> findAll();
    Articulo save(Articulo articulo);
    void deleteById(Long id);
    List<Articulo> findByAutorId(Long usuario); // Buscar artículos por ID de autor
}
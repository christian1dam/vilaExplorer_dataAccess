package app.VilaExplorer.service;


import app.VilaExplorer.domain.Articulo;
import app.VilaExplorer.exception.ArticuloNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;

import java.util.List;

public interface ArticuloService {
    Articulo findById(Long id) throws ArticuloNotFoundException;
    List<Articulo> findAll() throws ArticuloNotFoundException;
    Articulo save(Articulo articulo);
    void deleteById(Long id) throws ArticuloNotFoundException;
    List<Articulo> findByAutor(Long usuario) throws UsuarioNotFoundException; // Buscar artículos por ID de autor
}
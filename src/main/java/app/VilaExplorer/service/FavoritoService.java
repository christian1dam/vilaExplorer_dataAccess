package app.VilaExplorer.service;


import app.VilaExplorer.domain.Favorito;

import java.util.List;
import java.util.Optional;

public interface FavoritoService {
    Optional<Favorito> findById(Long id);
    List<Favorito> findAll();
    Favorito save(Favorito favorito);
    void deleteById(Long id);
}
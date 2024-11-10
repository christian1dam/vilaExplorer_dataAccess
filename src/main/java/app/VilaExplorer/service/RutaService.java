package app.VilaExplorer.service;


import app.VilaExplorer.domain.Ruta;

import java.util.List;
import java.util.Optional;

public interface RutaService {
    Optional<Ruta> findById(Long id);
    List<Ruta> findAll();
    Ruta save(Ruta ruta);
    void deleteById(Long id);

    //Buscar rutas creadas por un usuario específico
    List<Ruta> findByAutorId(Long autorId);
}

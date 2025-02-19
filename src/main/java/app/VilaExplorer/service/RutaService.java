package app.VilaExplorer.service;


import app.VilaExplorer.domain.Ruta;
import app.VilaExplorer.exception.RutaNotFoundException;

import java.util.List;
import java.util.Optional;

public interface RutaService {
    Optional<Ruta> findById(Long id);
    List<Ruta> findAll();
    Ruta save(Ruta ruta);
    void deleteById(Long id);

    //Buscar rutas creadas por un usuario específico
    List<Ruta> findByAutorId(Long autorId);

    //Actualizar una ruta
    Ruta updateRuta(Long id, Ruta rutaDetails) throws RutaNotFoundException;

    // Borrado lógico
    void deleteByIdLogico(Long id) throws RutaNotFoundException;

    Ruta desactivarRuta(Long id) throws RutaNotFoundException;
    Ruta activarRuta(Long id) throws RutaNotFoundException;

    // Mtodo opcional para obtener solo las rutas activas
    List<Ruta> findAllActivas();

    //metodo para actualizar rutas que no son predefinidas
    Ruta updateRutaNoPredefinida(Long id, Ruta rutaDetails) throws RutaNotFoundException;

    //  Obtener rutas del usuario + predefinidas
    List<Ruta> findRutasForUser(Long autorId);

    // Obtener todas las rutas inactivas
    List<Ruta> findAllInactivas();
}

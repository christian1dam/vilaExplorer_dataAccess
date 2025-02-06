package app.VilaExplorer.service;


import app.VilaExplorer.domain.Ruta;
import app.VilaExplorer.exception.RutaNotFoundException;
import app.VilaExplorer.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class
RutaServiceImpl implements RutaService {
    @Autowired
    private RutaRepository rutaRepository;

    @Override
    public Optional<Ruta> findById(Long id) {
        return rutaRepository.findById(id);
    }

    @Override
    public List<Ruta> findAll() {
        return rutaRepository.findAll();
    }

    @Override
    public Ruta save(Ruta ruta) {
        return rutaRepository.save(ruta);
    }

    @Override
    public void deleteById(Long id) {
        rutaRepository.deleteById(id);
    }

    @Override
    public List<Ruta> findByAutorId(Long autorId) {
        return rutaRepository.findByAutor_IdUsuario(autorId);
    }

    // NUEVO MTODO
    @Override
    public Ruta updateRuta(Long id, Ruta rutaDetails) throws RutaNotFoundException {
        // 1. Verificar si la ruta existe
        Ruta rutaFromDB = rutaRepository.findById(id)
                .orElseThrow(() -> new RutaNotFoundException("No se encontró la ruta con ID: " + id));

        // 2. Actualizar los atributos que desees
        rutaFromDB.setNombreRuta(rutaDetails.getNombreRuta());
        rutaFromDB.setAutor(rutaDetails.getAutor());
        // Aquí actualizas cualquier otro campo de ruta que tengas

        // 3. Manejar la lógica de coordenadas
        //    (Vaciar las que existen y reemplazarlas por las nuevas que lleguen)
        rutaFromDB.getCoordenadas().clear();
        rutaFromDB.getCoordenadas().addAll(rutaDetails.getCoordenadas());
        // Asignar la ruta a cada coordenada para la relación bidireccional
        rutaDetails.getCoordenadas().forEach(coordenada -> coordenada.setRuta(rutaFromDB));

        // 4. Guardar y devolver
        return rutaRepository.save(rutaFromDB);
    }

    @Override
    public List<Ruta> findAllActivas() {
        // Este solo devuelve las que tengan activo = true
        return rutaRepository.findAllByActivoTrue();
    }

    @Override
    public void deleteByIdLogico(Long id) throws RutaNotFoundException {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RutaNotFoundException("No se encontró la ruta con ID: " + id));
        ruta.setActivo(false);
        rutaRepository.save(ruta);
    }
    @Override
    public Ruta desactivarRuta(Long id) throws RutaNotFoundException {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RutaNotFoundException("No se encontró la ruta con ID: " + id));
        ruta.setActivo(false);
        return rutaRepository.save(ruta);
    }

    @Override
    public Ruta activarRuta(Long id) throws RutaNotFoundException {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RutaNotFoundException("No se encontró la ruta con ID: " + id));
        ruta.setActivo(true);
        return rutaRepository.save(ruta);
    }

    // NUEVO MTODO para actualizar rutas que no son predefinidas
    @Override
    public Ruta updateRutaNoPredefinida(Long id, Ruta rutaDetails) throws RutaNotFoundException {
        // 1. Verificar si la ruta existe
        Ruta rutaFromDB = rutaRepository.findByIdRutaAndPredefinidaFalse(id)
                .orElseThrow(() -> new RutaNotFoundException("No se encontró la ruta (O es predifinida), con ID: " + id));

        // 2. Actualizar los atributos que se desee
        rutaFromDB.setNombreRuta(rutaDetails.getNombreRuta());
        rutaFromDB.setAutor(rutaDetails.getAutor());
        // Aquí  se podria actualizar cualquier otro campo de ruta que se tenga

        // 3. Manejar la lógica de coordenadas
        //    (Vaciar las que existen y reemplazarlas por las nuevas que lleguen)
        rutaFromDB.getCoordenadas().clear();
        rutaFromDB.getCoordenadas().addAll(rutaDetails.getCoordenadas());
        // Asignar la ruta a cada coordenada para la relación bidireccional
        rutaDetails.getCoordenadas().forEach(coordenada -> coordenada.setRuta(rutaFromDB));

        // 4. Guardar y devolver
        return rutaRepository.save(rutaFromDB);
    }

    // NUEVO MTODO para obtener rutas del usuario + predefinidas
    @Override
    public List<Ruta> findRutasForUser(Long autorId) {
        // Rutas creadas por el usuario
        List<Ruta> rutasUsuario = rutaRepository.findByAutor_IdUsuario(autorId);
        // Rutas predefinidas disponibles para todos
        List<Ruta> rutasPredefinidas = rutaRepository.findByPredefinidaTrue();
        // Unir ambas listas
        rutasUsuario.addAll(rutasPredefinidas);
        return rutasUsuario;
    }

    // NUEVO MTODO para obtener todas las rutas inactivas
    @Override
    public List<Ruta> findAllInactivas() {
        return rutaRepository.findAllByActivoFalse();
    }



}

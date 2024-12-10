package app.VilaExplorer.service;

import app.VilaExplorer.domain.Puntuacion;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.exception.FiestaTradicionNotFound;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.repository.PuntuacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PuntuacionServiceImpl implements PuntuacionService {

    @Autowired
    private PuntuacionRepository puntuacionRepository;

    @Autowired
    private PlatoService platoService; // Servicio de platos

    @Autowired
    private FiestaTradicionService fiestaTradicionService; // Servicio de fiestas y tradiciones

    @Autowired
    private LugarInteresService lugarInteresService; // Servicio de lugares de interés


    /* Metodo que devuelve todas las puntuaciones de una entidad
    Se necesita el ID de la entidad y el tipo de entidad
        * @param idEntidad ID de la entidad
        * @param tipoEntidad Tipo de entidad
        * @return Lista de puntuaciones de la entidad
    */
    @Override
    public List<Puntuacion> findAllByEntidad(Long idEntidad, TipoEntidad tipoEntidad) {
        return puntuacionRepository.findByIdEntidadAndTipoEntidad(idEntidad, tipoEntidad);
    }

    /*
    Metodo que devuelve el promedio de calificación de una entidad
    Se necesita el ID de la entidad y el tipo de entidad
        * @param idEntidad ID de la entidad
        * @param tipoEntidad Tipo de entidad
        * @return Promedio de calificación de la entidad
       */
    @Override
    public Optional<Double> getPromedioCalificacion(Long idEntidad, TipoEntidad tipoEntidad) {
        List<Puntuacion> puntuaciones = findAllByEntidad(idEntidad, tipoEntidad);
        return puntuaciones.isEmpty() ? Optional.empty() :
                Optional.of(puntuaciones.stream()
                        .mapToInt(Puntuacion::getPuntuacion)
                        .average()
                        .orElse(0.0));
    }

    /*
    * Este metodo devuelve el conteo de cada valor de calificación para una entidad
    * Se necesita el ID de la entidad y el tipo de entidad
    * @param idEntidad ID de la entidad
    * @param tipoEntidad Tipo de entidad
    * @return Mapa con el conteo de cada valor de calificación
    * Sirve para mostrar la cantidad de calificaciones de cada estrella e un lugar o plato
     */
    @Override
    public Map<Integer, Long> getConteoCalificacionesPorEstrella(Long idEntidad, TipoEntidad tipoEntidad) {
        List<Puntuacion> puntuaciones = findAllByEntidad(idEntidad, tipoEntidad);
        return puntuaciones.stream()
                .collect(Collectors.groupingBy(
                        Puntuacion::getPuntuacion,
                        Collectors.counting()
                ));
    }

    /*
    * Este metodo devuelve las puntuaciones de un usuario para una entidad específica
    * se necesita el ID del usuario, el ID de la entidad y el tipo de entidad
    * @param idUsuario ID del usuario
    * @param idEntidad ID de la entidad
    * @param tipoEntidad Tipo de entidad
    * @return Lista de puntuaciones del usuario para la entidad
    * Sirve para mostrar las calificaciones que un usuario ha dado a un lugar o plato
    */
    @Override
    public List<Puntuacion> findByUsuarioAndEntidad(Long idUsuario, Long idEntidad, TipoEntidad tipoEntidad) {
        return puntuacionRepository.findByUsuario_IdUsuarioAndIdEntidadAndTipoEntidad(idUsuario, idEntidad, tipoEntidad);
    }

    @Override
    public List<Puntuacion> findAllByUsuario(Long idUsuario) {
        return puntuacionRepository.findByUsuario_IdUsuario(idUsuario);
    }

    /*
        * Este metodo filtra las entidades que tengan un promedio de calificación igual o superior a un valor específico.
        * @param tipoEntidad Tipo de entidad a filtrar
        * @param calificacionMinima Calificación mínima requerida
        * @return Lista de IDs de entidades que cumplen con el criterio de filtrado
        *
     */
    @Override
    public List<Long> findEntidadesConCalificacionMinima(TipoEntidad tipoEntidad, double calificacionMinima) {
        List<Long> entidadesFiltradas = new ArrayList<>();
        List<Puntuacion> puntuaciones = puntuacionRepository.findByTipoEntidad(tipoEntidad);
        Map<Long, List<Puntuacion>> puntuacionesAgrupadas = puntuaciones.stream()
                .collect(Collectors.groupingBy(Puntuacion::getIdEntidad));

        for (Map.Entry<Long, List<Puntuacion>> entry : puntuacionesAgrupadas.entrySet()) {
            double promedio = entry.getValue().stream()
                    .mapToInt(Puntuacion::getPuntuacion)
                    .average()
                    .orElse(0.0);
            if (promedio >= calificacionMinima) {
                entidadesFiltradas.add(entry.getKey());
            }
        }
        return entidadesFiltradas;
    }

    //----- Metodos para actualizar y/o crear puntuaciones

    //Metodo que Crea y/o Actualiza la puntuación de un usuario para una entidad específica este es el recomendado usar
    @Override
    public Puntuacion updatePuntuacion(Long idUsuario, Long idEntidad, TipoEntidad tipoEntidad, Integer nuevaPuntuacion) throws RuntimeException, PlatoNotFoundException, FiestaTradicionNotFound {
        Puntuacion puntuacion = puntuacionRepository
                .findByUsuario_IdUsuarioAndIdEntidadAndTipoEntidad(idUsuario, idEntidad, tipoEntidad)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Puntuación no encontrada para la entidad especificada"));

        puntuacion.setPuntuacion(nuevaPuntuacion);
        Puntuacion updatedPuntuacion = puntuacionRepository.save(puntuacion);

        // Actualizar la puntuación media según el tipo de entidad
        if (tipoEntidad == TipoEntidad.PLATO) {
            platoService.actualizarPuntuacionMediaPlato(idEntidad);
        } else if (tipoEntidad == TipoEntidad.FIESTA_TRADICION) {
            fiestaTradicionService.actualizarPuntuacionMediaTradicion(idEntidad);
        } else if (tipoEntidad == TipoEntidad.LUGAR_INTERES) {
            lugarInteresService.actualizarPuntuacionMediaLugarInteres(idEntidad);
        }

        return updatedPuntuacion;
    }

    @Override
    public Puntuacion createPuntuacion(Puntuacion puntuacion) throws RuntimeException, PlatoNotFoundException, FiestaTradicionNotFound {
        Puntuacion nuevaPuntuacion = puntuacionRepository.save(puntuacion);

        // Actualizar la puntuación media según el tipo de entidad
        if (puntuacion.getTipoEntidad() == TipoEntidad.PLATO) {
            platoService.actualizarPuntuacionMediaPlato(puntuacion.getIdEntidad());
        } else if (puntuacion.getTipoEntidad() == TipoEntidad.FIESTA_TRADICION) {
            fiestaTradicionService.actualizarPuntuacionMediaTradicion(puntuacion.getIdEntidad());
        } else if (puntuacion.getTipoEntidad() == TipoEntidad.LUGAR_INTERES) {
            lugarInteresService.actualizarPuntuacionMediaLugarInteres(puntuacion.getIdEntidad());
        }

        return nuevaPuntuacion;
    }

    //Metodos especificos para el calculo de la calificacion media de cada entidad PLato, Lugar de interes, Fiesta tradicion

    @Override
    public Optional<Double> getPromedioCalificacionPlato(Long idPlato) {
        return getPromedioCalificacion(idPlato, TipoEntidad.PLATO);
    }

    @Override
    public Optional<Double> getPromedioCalificacionTradicion(Long idTradicion) {
        return getPromedioCalificacion(idTradicion, TipoEntidad.FIESTA_TRADICION);
    }

    @Override
    public Optional<Double> getPromedioCalificacionLugarInteres(Long idLugarInteres) {
        return getPromedioCalificacion(idLugarInteres, TipoEntidad.LUGAR_INTERES);
    }


}

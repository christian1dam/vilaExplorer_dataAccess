package app.VilaExplorer.service;

import app.VilaExplorer.domain.Puntuacion;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.exception.FiestaTradicionNotFound;
import app.VilaExplorer.exception.PlatoNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PuntuacionService {

    // Obtener todas las puntuaciones de una entidad
    List<Puntuacion> findAllByEntidad(Long idEntidad, TipoEntidad tipoEntidad);

    // Obtener promedio de calificación para una entidad
    Optional<Double> getPromedioCalificacion(Long idEntidad, TipoEntidad tipoEntidad);

    // Obtener el conteo de cada valor de calificación para una entidad
    Map<Integer, Long> getConteoCalificacionesPorEstrella(Long idEntidad, TipoEntidad tipoEntidad);

    // Obtener las puntuaciones de un usuario para una entidad específica
    List<Puntuacion> findByUsuarioAndEntidad(Long idUsuario, Long idEntidad, TipoEntidad tipoEntidad);

    // Obtener todas las puntuaciones de un usuario
    List<Puntuacion> findAllByUsuario(Long idUsuario);

    // Filtrar entidades que tengan un promedio de calificación igual o superior a un valor específico
    List<Long> findEntidadesConCalificacionMinima(TipoEntidad tipoEntidad, double calificacionMinima);



    //-------------------------------------------------------------------------------------------------------------

    /**
     * Actualiza la puntuación de un usuario para una entidad específica
     * @param idUsuario ID del usuario que realiza la calificación
     * @param idEntidad ID de la entidad calificada
     * @param tipoEntidad Tipo de entidad calificada (PLATO, LUGAR_INTERES, etc.)
     * @param nuevaPuntuacion Nueva calificación a asignar
     * @return Puntuacion actualizada
     */
    Puntuacion updatePuntuacion(Long idUsuario, Long idEntidad, TipoEntidad tipoEntidad, Integer nuevaPuntuacion) throws PlatoNotFoundException, FiestaTradicionNotFound;


    /**
     * Crea una nueva puntuación para una entidad específica
     * @param puntuacion Nueva puntuación a crear
     * @return Puntuacion creada
     */
    Puntuacion createPuntuacion(Puntuacion puntuacion) throws PlatoNotFoundException, FiestaTradicionNotFound;

    //-------------------------------------------------------------------------------------------------------------




    //Metodos especificos para el calculo de la calificacion media de cada entidad PLato, Lugar de interes, Fiesta tradicion
    // Obtener promedio de calificación para un plato
    Optional<Double> getPromedioCalificacionPlato(Long idPlato);

    // Obtener promedio de calificación para una tradición
    Optional<Double> getPromedioCalificacionTradicion(Long idTradicion);

    // Obtener promedio de calificación para un lugar de interés
    Optional<Double> getPromedioCalificacionLugarInteres(Long idLugarInteres);

//-----------------------------------------------------------------------------------------------------------
    Optional<Puntuacion> usuarioHaPuntuadoEsteObjeto(Long idUsuario, Long idEntidad, String tipoEntidad);

}

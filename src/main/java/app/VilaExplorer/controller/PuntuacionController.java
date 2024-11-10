package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Puntuacion;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.service.PuntuacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/puntuaciones")
public class PuntuacionController {

    @Autowired
    private PuntuacionService puntuacionService;

    // Obtener todas las puntuaciones de una entidad específica
    @GetMapping("/entidad/{tipoEntidad}/{idEntidad}")
    public ResponseEntity<List<Puntuacion>> getAllByEntidad(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Long idEntidad) {
        List<Puntuacion> puntuaciones = puntuacionService.findAllByEntidad(idEntidad, tipoEntidad);
        return puntuaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(puntuaciones);
    }

    // Obtener promedio de calificación para una entidad específica
    @GetMapping("/promedio/{tipoEntidad}/{idEntidad}")
    public ResponseEntity<Double> getPromedioCalificacion(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Long idEntidad) {
        Optional<Double> promedio = puntuacionService.getPromedioCalificacion(idEntidad, tipoEntidad);
        return promedio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    // Obtener el conteo de calificaciones por estrella para una entidad específica
    @GetMapping("/conteo/{tipoEntidad}/{idEntidad}")
    public ResponseEntity<Map<Integer, Long>> getConteoCalificacionesPorEstrella(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Long idEntidad) {
        Map<Integer, Long> conteo = puntuacionService.getConteoCalificacionesPorEstrella(idEntidad, tipoEntidad);
        return conteo.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(conteo);
    }

    // Obtener las puntuaciones de un usuario para una entidad específica
    @GetMapping("/usuario/{idUsuario}/entidad/{tipoEntidad}/{idEntidad}")
    public ResponseEntity<List<Puntuacion>> getPuntuacionesByUsuarioAndEntidad(
            @PathVariable Long idUsuario,
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Long idEntidad) {
        List<Puntuacion> puntuaciones = puntuacionService.findByUsuarioAndEntidad(idUsuario, idEntidad, tipoEntidad);
        return puntuaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(puntuaciones);
    }

    // Obtener todas las puntuaciones realizadas por un usuario en todas las entidades
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Puntuacion>> getAllPuntuacionesByUsuario(@PathVariable Long idUsuario) {
        List<Puntuacion> puntuaciones = puntuacionService.findAllByUsuario(idUsuario);
        return puntuaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(puntuaciones);
    }

    // Filtrar entidades de un tipo específico que tengan una calificación mínima
    @GetMapping("/entidades-con-calificacion/{tipoEntidad}/{calificacionMinima}")
    public ResponseEntity<List<Long>> getEntidadesConCalificacionMinima(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable double calificacionMinima) {
        List<Long> entidades = puntuacionService.findEntidadesConCalificacionMinima(tipoEntidad, calificacionMinima);
        return entidades.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entidades);
    }

    /**
            * Endpoint para actualizar una calificación de una entidad específica por un usuario.
            * @param idUsuario ID del usuario que actualiza la calificación
     * @param idEntidad ID de la entidad calificada
     * @param tipoEntidad Tipo de entidad (PLATO, LUGAR_INTERES, FIESTA_TRADICION)
     * @param nuevaPuntuacion Nueva calificación
     * @return Puntuacion actualizada
     */
    @PutMapping("/actualizar")
    public ResponseEntity<Puntuacion> actualizarPuntuacion(
            @RequestParam Long idUsuario,
            @RequestParam Long idEntidad,
            @RequestParam TipoEntidad tipoEntidad,
            @RequestParam Integer nuevaPuntuacion) {

        Puntuacion puntuacionActualizada = puntuacionService.updatePuntuacion(idUsuario, idEntidad, tipoEntidad, nuevaPuntuacion);
        return ResponseEntity.ok(puntuacionActualizada);
    }


}

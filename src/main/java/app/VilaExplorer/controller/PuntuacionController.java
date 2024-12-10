package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Puntuacion;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.exception.FiestaTradicionNotFound;
import app.VilaExplorer.exception.LugarInteresNotFoundException;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.service.PuntuacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
/**
 * Controlador para la API REST de Puntuaciones.
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@RestController
@Tag(name = "Puntuaciones", description = "API para la gestion de puntuaciones del sistema")
@RequestMapping("/puntuacion")
public class PuntuacionController {

    @Autowired
    private PuntuacionService puntuacionService;

    //-----GET----- OBTENER PUNTUACIONES

    // Obtener todas las puntuaciones de una entidad específica
    @Operation(summary = "Obtiene todas las puntuaciones de una entidad especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puntuaciones encontradas", content = @Content(schema = @Schema(implementation = Puntuacion.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron puntuaciones", content = @Content)
    })
    @GetMapping("/entidad/{tipoEntidad}/{idEntidad}")
    public ResponseEntity<List<Puntuacion>> getAllByEntidad(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Long idEntidad) {
        List<Puntuacion> puntuaciones = puntuacionService.findAllByEntidad(idEntidad, tipoEntidad);
        return puntuaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(puntuaciones);
    }

    // Obtener promedio de calificación para una entidad específica
    @Operation(summary = "Obtiene el promedio de calificacion para una entidad especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promedio de calificacion encontrado", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron calificaciones", content = @Content)
    })
    @GetMapping("/promedio/{tipoEntidad}/{idEntidad}")
    public ResponseEntity<Double> getPromedioCalificacion(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Long idEntidad) {
        Optional<Double> promedio = puntuacionService.getPromedioCalificacion(idEntidad, tipoEntidad);
        return promedio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    // Obtener el conteo de calificaciones por estrella para una entidad específica
    @Operation(summary = "Obtiene el conteo de calificaciones por estrella para una entidad especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo de calificaciones encontrado", content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron calificaciones", content = @Content)
    })
    @GetMapping("/conteo/{tipoEntidad}/{idEntidad}")
    public ResponseEntity<Map<Integer, Long>> getConteoCalificacionesPorEstrella(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Long idEntidad) {
        Map<Integer, Long> conteo = puntuacionService.getConteoCalificacionesPorEstrella(idEntidad, tipoEntidad);
        return conteo.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(conteo);
    }

    // Obtener las puntuaciones de un usuario para una entidad específica
    @Operation(summary = "Obtiene las puntuaciones de un usuario para una entidad especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puntuaciones encontradas", content = @Content(schema = @Schema(implementation = Puntuacion.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron puntuaciones", content = @Content)
    })
    @GetMapping("/usuario/{idUsuario}/entidad/{tipoEntidad}/{idEntidad}")
    public ResponseEntity<List<Puntuacion>> getPuntuacionesByUsuarioAndEntidad(
            @PathVariable Long idUsuario,
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable Long idEntidad) {
        List<Puntuacion> puntuaciones = puntuacionService.findByUsuarioAndEntidad(idUsuario, idEntidad, tipoEntidad);
        return puntuaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(puntuaciones);
    }

    // Obtener todas las puntuaciones realizadas por un usuario en todas las entidades
    @Operation(summary = "Obtiene todas las puntuaciones realizadas por un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puntuaciones encontradas", content = @Content(schema = @Schema(implementation = Puntuacion.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron puntuaciones", content = @Content)
    })
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Puntuacion>> getAllPuntuacionesByUsuario(@PathVariable Long idUsuario) {
        List<Puntuacion> puntuaciones = puntuacionService.findAllByUsuario(idUsuario);
        return puntuaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(puntuaciones);
    }

    // Filtrar entidades de un tipo específico que tengan una calificación mínima
    @Operation(summary = "Filtra entidades de un tipo especifico con una calificacion minima")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entidades encontradas", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron entidades con la calificacion minima", content = @Content)
    })
    @GetMapping("/entidades-con-calificacion/{tipoEntidad}/{calificacionMinima}")
    public ResponseEntity<List<Long>> getEntidadesConCalificacionMinima(
            @PathVariable TipoEntidad tipoEntidad,
            @PathVariable double calificacionMinima) {
        List<Long> entidades = puntuacionService.findEntidadesConCalificacionMinima(tipoEntidad, calificacionMinima);
        return entidades.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(entidades);
    }


    //-----POST----- CREAR PUNTUACIONES

    @Operation(summary = "Crea una nueva puntuación para una entidad específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Puntuación creada", content = @Content(schema = @Schema(implementation = Puntuacion.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados inválidos", content = @Content)
    })
    @PostMapping("/crear")
    public ResponseEntity<Puntuacion> createPuntuacion(@RequestBody Puntuacion puntuacion) {
        try {
            Puntuacion nuevaPuntuacion = puntuacionService.createPuntuacion(puntuacion);
            return new ResponseEntity<>(nuevaPuntuacion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /**
    * Endpoint para actualizar una calificación de una entidad específica por un usuario.
     * @param idUsuario ID del usuario que actualiza la calificación
     * @param idEntidad ID de la entidad calificada
     * @param tipoEntidad Tipo de entidad (PLATO, LUGAR_INTERES, FIESTA_TRADICION)
     * @param nuevaPuntuacion Nueva calificación
     * @return Puntuacion actualizada
     */
    @Operation(summary = "Actualiza una calificacion de una entidad especifica por un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puntuacion actualizada", content = @Content(schema = @Schema(implementation = Puntuacion.class))),
            @ApiResponse(responseCode = "404", description = "Puntuacion no encontrada", content = @Content)
    })
    @PutMapping("/actualizar")
    public ResponseEntity<Puntuacion> actualizarPuntuacion(
            @RequestParam Long idUsuario,
            @RequestParam Long idEntidad,
            @RequestParam TipoEntidad tipoEntidad,
            @RequestParam Integer nuevaPuntuacion) throws PlatoNotFoundException, FiestaTradicionNotFound, LugarInteresNotFoundException {

        Puntuacion puntuacionActualizada = puntuacionService.updatePuntuacion(idUsuario, idEntidad, tipoEntidad, nuevaPuntuacion);
        return ResponseEntity.ok(puntuacionActualizada);
    }


    //------------------------------------------------------------------------------------------------

    //Endopoint para obtener el promedio de calificacion  de una entidad especifica
    @GetMapping("/promedio/plato/{idPlato}")
    public ResponseEntity<Double> getPromedioCalificacionPlato(@PathVariable Long idPlato) {
        Optional<Double> promedio = puntuacionService.getPromedioCalificacionPlato(idPlato);
        return promedio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/promedio/tradicion/{idTradicion}")
    public ResponseEntity<Double> getPromedioCalificacionTradicion(@PathVariable Long idTradicion) {
        Optional<Double> promedio = puntuacionService.getPromedioCalificacionTradicion(idTradicion);
        return promedio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/promedio/lugar-interes/{idLugarInteres}")
    public ResponseEntity<Double> getPromedioCalificacionLugarInteres(@PathVariable Long idLugarInteres) {
        Optional<Double> promedio = puntuacionService.getPromedioCalificacionLugarInteres(idLugarInteres);
        return promedio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }



}

package app.VilaExplorer.controller;

import app.VilaExplorer.domain.LugarInteres;
import app.VilaExplorer.service.LugarInteresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * Controlador para la API REST de Lugares de Interes.
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@RestController
@Tag(name = "Lugares de Interes", description = "API para la gestion de lugares de interes del sistema")
@RequestMapping("/api/lugares")
public class LugarInteresController {

    @Autowired
    private LugarInteresService lugarInteresService;

     @Operation(summary = "Obtiene un lugar de interes por su ID")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Lugar de interes encontrado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
             @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content)
     })
    @GetMapping("/detalle-completo/{id}")
    public ResponseEntity<LugarInteres> getLugarInteresById(@PathVariable Long id) {
        Optional<LugarInteres> lugarInteres = lugarInteresService.findById(id);
        return lugarInteres.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Obtiene un lugar de interes activo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes activo encontrado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado o no esta activo", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    public ResponseEntity<LugarInteres> getLugarInteresActivoById(@PathVariable Long id) {
        Optional<LugarInteres> lugarInteres = lugarInteresService.findById(id);
        return lugarInteres.filter(LugarInteres::getActivo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @Operation(summary = "Obtiene todos los lugares de interes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de lugares de interes", content = @Content(schema = @Schema(implementation = LugarInteres.class)))
    })
    @GetMapping("/todos")
    public List<LugarInteres> getAllLugaresInteres() {
        return lugarInteresService.findAll();
    }



    @Operation(summary = "Obtiene todos los lugares de interes activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de lugares de interes activos", content = @Content(schema = @Schema(implementation = LugarInteres.class)))
    })
    @GetMapping("/activos")
    public List<LugarInteres> getAllLugaresInteresActivos() {
        return lugarInteresService.findAllActivos();
    }


    @Operation(summary = "Crea un nuevo lugar de interes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes creado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados invalidos o faltan coordenadas", content = @Content)
    })
    @PostMapping("/crear")
    public ResponseEntity<LugarInteres> createLugarInteres(@RequestBody LugarInteres lugarInteres) {
        // Validar que se hayan enviado coordenadas
        if (lugarInteres.getCoordenadas() == null || lugarInteres.getCoordenadas().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Es obligatorio tener al menos un par de coordenadas
        }

        // Asociar cada coordenada al lugar de interés
        lugarInteres.getCoordenadas().forEach(coordenada -> coordenada.setLugarInteres(lugarInteres));

        // Guardar el lugar de interés junto con las coordenadas asociadas
        LugarInteres savedLugarInteres = lugarInteresService.save(lugarInteres);
        return ResponseEntity.ok(savedLugarInteres);
    }


    @Operation(summary = "Modifica un lugar de interes por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes modificado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    public ResponseEntity<LugarInteres> updateLugarInteres(@PathVariable Long id, @RequestBody LugarInteres lugarInteresDetalle) {
        LugarInteres lugarInteres = lugarInteresService.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el lugar de interés con el id: " + id));

        if (!lugarInteres.getActivo()) {
            return ResponseEntity.notFound().build();
        }

        // Actualizando los atributos de LugarInteres
        lugarInteres.setNombreLugar(lugarInteresDetalle.getNombreLugar());
        lugarInteres.setDescripcion(lugarInteresDetalle.getDescripcion());
        lugarInteres.setImagen(lugarInteresDetalle.getImagen());
        lugarInteres.setTipoLugar(lugarInteresDetalle.getTipoLugar());
        lugarInteres.setFechaAlta(lugarInteresDetalle.getFechaAlta());

        // Gestionar las coordenadas: eliminar las existentes y añadir las nuevas
        lugarInteres.getCoordenadas().clear();
        lugarInteres.getCoordenadas().addAll(lugarInteresDetalle.getCoordenadas());
        lugarInteresDetalle.getCoordenadas().forEach(coordenada -> coordenada.setLugarInteres(lugarInteres));

        return ResponseEntity.ok(lugarInteresService.save(lugarInteres));
    }


    @Operation(summary = "Desactiva un lugar de interes por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes desactivado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content)
    })
    @PutMapping("/desactivar/{id}")
    public ResponseEntity<LugarInteres> desactivarLugarInteres(@PathVariable Long id) {
        LugarInteres lugarInteres = lugarInteresService.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el lugar de interés con el id: " + id));

        // Cambiar el estado a inactivo (borrado lógico)
        lugarInteres.setActivo(false);

        return ResponseEntity.ok(lugarInteresService.save(lugarInteres));
    }


    @Operation(summary = "Elimina un lugar de interes de forma logica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes eliminado logicamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteLugarInteres(@PathVariable Long id) {
        lugarInteresService.deleteByIdLogico(id);
        return ResponseEntity.ok("Lugar de interés eliminado lógicamente");
    }

}
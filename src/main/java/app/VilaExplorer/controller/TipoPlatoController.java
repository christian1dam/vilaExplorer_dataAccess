package app.VilaExplorer.controller;

import app.VilaExplorer.domain.TipoPlato;
import app.VilaExplorer.service.TipoPlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la API REST de Tipos de Plato.
 * @author VilaExplorerAdmin
 * @version 1.0
 */

@RestController
@RequestMapping("/api/tipos-plato")
public class TipoPlatoController {

    @Autowired
    private TipoPlatoService tipoPlatoService;

    // Obtener todos los tipos de plato (incluye los desactivados)
    @Operation(summary = "Obtiene todos los tipos de plato, incluyendo los desactivados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de plato encontrados", content = @Content(schema = @Schema(implementation = TipoPlato.class)))
    })
    @GetMapping("/all")
    public List<TipoPlato> getAllTiposPlato() {
        return tipoPlatoService.findAll();
    }


    // Obtener todos los tipos de plato activos
    @Operation(summary = "Obtiene todos los tipos de plato activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de plato activos encontrados", content = @Content(schema = @Schema(implementation = TipoPlato.class)))
    })
    @GetMapping("/activos")
    public List<TipoPlato> getAllTiposPlatoActivos() {
        return tipoPlatoService.findAllActivos();
    }


    // Obtener un tipo de plato por ID
    @Operation(summary = "Obtiene un tipo de plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de plato encontrado", content = @Content(schema = @Schema(implementation = TipoPlato.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de plato no encontrado", content = @Content)
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<TipoPlato> getTipoPlatoById(@PathVariable Long id) {
        Optional<TipoPlato> tipoPlato = tipoPlatoService.findById(id);
        return tipoPlato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Crear o actualizar un tipo de plato
    @Operation(summary = "Crea o actualiza un tipo de plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de plato creado o actualizado", content = @Content(schema = @Schema(implementation = TipoPlato.class)))
    })
    @PostMapping("/crear")
    public ResponseEntity<TipoPlato> createOrUpdateTipoPlato(@RequestBody TipoPlato tipoPlato) {
        TipoPlato savedTipoPlato = tipoPlatoService.save(tipoPlato);
        return ResponseEntity.ok(savedTipoPlato);
    }


    // Borrado lógico de un tipo de plato por ID
    @Operation(summary = "Elimina un tipo de plato de forma lógica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de plato eliminado lógicamente", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteTipoPlatoLogico(@PathVariable Long id) {
        tipoPlatoService.deleteByIdLogico(id);
        return ResponseEntity.ok("Tipo de plato eliminado lógicamente");
    }


    // Obtener todos los tipos de plato activos por ID de categoría
    @Operation(summary = "Obtiene todos los tipos de plato activos por ID de categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipos de plato activos encontrados para la categoría", content = @Content(schema = @Schema(implementation = TipoPlato.class)))
    })
    @GetMapping("/categoria/{categoriaId}")
    public List<TipoPlato> getTiposPlatoByCategoriaId(@PathVariable Long categoriaId) {
        return tipoPlatoService.findByCategoriaId(categoriaId);
    }
}

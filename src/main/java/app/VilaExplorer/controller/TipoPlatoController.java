package app.VilaExplorer.controller;

import app.VilaExplorer.domain.TipoPlato;
import app.VilaExplorer.exception.TipoPlatoNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.service.TipoPlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

/**
 * Controlador para la API REST de Tipos de Plato.
 *
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
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tipos de plato encontrados", content = @Content(schema = @Schema(implementation = TipoPlato.class)))})
    @GetMapping("/all")
    public ResponseEntity<List<TipoPlato>> getAllTiposPlato() {
        try {
            List<TipoPlato> tipoPlatoList = tipoPlatoService.findAll();
            return new ResponseEntity<>(tipoPlatoList, HttpStatus.OK);
        } catch (TipoPlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Obtener todos los tipos de plato activos
    @Operation(summary = "Obtiene todos los tipos de plato activos")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tipos de plato activos encontrados", content = @Content(schema = @Schema(implementation = TipoPlato.class)))})
    @GetMapping("/activos")
    public ResponseEntity<?> getAllTiposPlatoActivos() {
        try {
            List<TipoPlato> tipoPlatoList = tipoPlatoService.findAllActivos();
            return new ResponseEntity<>(tipoPlatoList, HttpStatus.OK);
        } catch (TipoPlatoNotFoundException e) {
            return handleException(e);
        }
    }


    // Obtener un tipo de plato por ID
    @Operation(summary = "Obtiene un tipo de plato por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tipo de plato encontrado", content = @Content(schema = @Schema(implementation = TipoPlato.class))), @ApiResponse(responseCode = "404", description = "Tipo de plato no encontrado", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<TipoPlato> getTipoPlatoById(@PathVariable Long id) {
        try {
            TipoPlato plato = tipoPlatoService.findById(id);
            return new ResponseEntity<>(plato, HttpStatus.OK);
        } catch (TipoPlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Crear o actualizar un tipo de plato
    @Operation(summary = "Crea o actualiza un tipo de plato")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tipo de plato creado o actualizado", content = @Content(schema = @Schema(implementation = TipoPlato.class)))})
    @PostMapping("/crear")
    public ResponseEntity<TipoPlato> createOrUpdateTipoPlato(@RequestBody TipoPlato tipoPlato) {
        TipoPlato savedTipoPlato = tipoPlatoService.save(tipoPlato);
        return ResponseEntity.ok(savedTipoPlato);
    }


    // Borrado lógico de un tipo de plato por ID
    @Operation(summary = "Elimina un tipo de plato de forma lógica")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tipo de plato eliminado lógicamente", content = @Content)})
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Response> deleteTipoPlatoLogico(@PathVariable Long id) {
        try {
            tipoPlatoService.deleteByIdLogico(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TipoPlatoNotFoundException e) {
            return handleException(e);
        }
    }


    // Obtener todos los tipos de plato activos por ID de categoría
    @Operation(summary = "Obtiene todos los tipos de plato activos por ID de categoría")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Tipos de plato activos encontrados para la categoría", content = @Content(schema = @Schema(implementation = TipoPlato.class)))})
    @GetMapping("/categoria/{categoriaId}")
    public List<TipoPlato> getTiposPlatoByCategoriaId(@PathVariable Long categoriaId) {
        return tipoPlatoService.findByCategoriaId(categoriaId);
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<TipoPlato> activarTipoPlato(@PathVariable Long id, @RequestParam(value = "activo") String activo){
        try{
            TipoPlato tipoPlato = tipoPlatoService.activarTipoPlato(id, activo);
            return new ResponseEntity<>(tipoPlato, HttpStatus.OK);
        } catch (TipoPlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ExceptionHandler(TipoPlatoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(TipoPlatoNotFoundException tpnfe) {
        Response response = Response.errorResponse(NOT_FOUND, tpnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

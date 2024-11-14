package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.service.PlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

/**
 * Controlador para la API REST de Platos.
 *
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@Controller
@Tag(name = "Lugares de Interes", description = "API para la gestion de lugares de interes del sistema")
@RequestMapping("/api/platos")
public class PlatoController {

    @Autowired
    private PlatoService platoService;

    @Operation(summary = "Obtiene un plato por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plato encontrado", content = @Content(schema = @Schema(implementation = Plato.class))), @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)})
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Plato> getPlatoById(@PathVariable Long id) {
        try {
            Plato plato = platoService.findById(id);
            return new ResponseEntity<>(plato, HttpStatus.OK);
        } catch (PlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtiene todos los platos")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Listado de platos", content = @Content(schema = @Schema(implementation = Plato.class)))})
    @GetMapping("/todos")
    public ResponseEntity<List<Plato>> getAllPlatos() {
        try {
            List<Plato> platos = platoService.findAll();
            return new ResponseEntity<>(platos, HttpStatus.OK);
        } catch (PlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crea un nuevo plato")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plato creado", content = @Content(schema = @Schema(implementation = Plato.class))), @ApiResponse(responseCode = "400", description = "Datos proporcionados invalidos", content = @Content)})
    @PostMapping("/crear")
    public ResponseEntity<Plato> createPlato(@RequestBody Plato plato) {
        try {
            Plato platoDB = platoService.createPlato(plato);
            return new ResponseEntity<>(platoService.save(plato), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // actualizar un plato existente
    @Operation(summary = "Modifica un plato por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plato modificado", content = @Content(schema = @Schema(implementation = Plato.class))), @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)})
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Plato> updatePlato(@PathVariable Long id, @RequestBody Plato platoDetalles) {
        try {
            Plato platoUpdated = platoService.updatePlato(id, platoDetalles);
            return new ResponseEntity<>(platoUpdated, HttpStatus.OK);
        } catch (PlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Elimina un plato por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Plato eliminado", content = @Content), @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)})
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Response> deletePlato(@PathVariable Long id) {
        try {
            platoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PlatoNotFoundException e) {
            return handleException(e);
        }
    }

    @Operation(summary = "Aprueba un plato por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plato aprobado", content = @Content(schema = @Schema(implementation = Plato.class))), @ApiResponse(responseCode = "404", description = "Plato o aprobador no encontrado", content = @Content)})
    @PutMapping("/aprobar/{platoId}/{aprobadorId}")
    public ResponseEntity<Plato> aprobarPlato(@PathVariable Long platoId, @PathVariable Long aprobadorId) {
        try {
            Plato platoAprobado = platoService.aprobarPlato(platoId, aprobadorId);
            return new ResponseEntity<>(platoAprobado, HttpStatus.OK);
        } catch (PlatoNotFoundException | UsuarioNotFoundException | RolNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(PlatoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(PlatoNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
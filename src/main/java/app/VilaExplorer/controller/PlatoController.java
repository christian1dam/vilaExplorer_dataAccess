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
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/plato")
public class PlatoController {

    @Autowired
    private PlatoService platoService;

    //-----GET----- OBTENER PLATO POR ID

    @Operation(summary = "Obtiene un plato por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plato encontrado", content = @Content(schema = @Schema(implementation = Plato.class))), @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)})
    @GetMapping("/detalle/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor') or hasRole('Cliente')")
    public ResponseEntity<Plato> getPlatoById(@PathVariable Long id) {
        try {
            Plato plato = platoService.findById(id);
            return new ResponseEntity<>(plato, HttpStatus.OK);
        } catch (PlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //-----GET----- OBTENER TODOS LOS PLATOS INCLUYENDO LOS NO APROBADOS Y LOS ELMINADOS LOGICAMENTE
    @Operation(summary = "Obtiene todos los platos")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Listado de platos", content = @Content(schema = @Schema(implementation = Plato.class)))})
    @GetMapping("/todos")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<List<Plato>> getAllPlatos() {
        try {
            List<Plato> platos = platoService.findAll();
            return new ResponseEntity<>(platos, HttpStatus.OK);
        } catch (PlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener platos aprobados y no eliminados
    @Operation(summary = "Obtiene los platos aprobados y no eliminados")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Listado de platos aprobados", content = @Content(schema = @Schema(implementation = Plato.class)))})
    @GetMapping("/aprobados")
    @PreAuthorize("hasRole('Cliente')")
    public ResponseEntity<List<Plato>> getPlatosAprobados() {
        try {
            List<Plato> platos = platoService.findAprobadosNoEliminados();
            return new ResponseEntity<>(platos, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener platos no aprobados
    @GetMapping("/no-aprobados")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<List<Plato>> getPlatosNoAprobados() {
        try {
            List<Plato> platos = platoService.findNoAprobados();
            return platos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(platos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener platos eliminados
    @GetMapping("/eliminados")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<List<Plato>> getPlatosEliminados() {
        try {
            List<Plato> platos = platoService.findEliminados();
            return platos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(platos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener platos no aprobados y no eliminados
    @GetMapping("/no-aprobados-no-eliminados")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<List<Plato>> getPlatosNoAprobadosNoEliminados() {
        try {
            List<Plato> platos = platoService.findNoAprobadosNoEliminados();
            return platos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(platos);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    //-----POST----- CREAR PLATO
    @Operation(summary = "Crea un nuevo plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato creado", content = @Content(schema = @Schema(implementation = Plato.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados invalidos", content = @Content)
    })
    @PostMapping("/crear")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<?> createPlato(@RequestBody Plato plato) {
        try {
            platoService.createPlato(plato);
            return new ResponseEntity<>(plato, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




    //-----POST----- CREAR PLATO
    @Operation(summary = "Crea un nuevo plato")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plato creado", content = @Content(schema = @Schema(implementation = Plato.class))), @ApiResponse(responseCode = "400", description = "Datos proporcionados invalidos", content = @Content)})
    @PostMapping("/crearFlutter")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<Plato> createPlatoFlutter(@RequestBody Plato plato, @RequestParam(value = "autorID") Long autorID, @RequestParam(value = "tipoPlatoID") Long tipoPlatoID) {
        try {
            platoService.createPlato(plato);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //-----PUT----- ACTUALIZAR PLATO
    // actualizar un plato existente
    @Operation(summary = "Modifica un plato por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plato modificado", content = @Content(schema = @Schema(implementation = Plato.class))), @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)})
    @PutMapping("/modificar/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<Plato> updatePlato(@PathVariable Long id, @RequestBody Plato platoDetalles) {
        try {
            Plato platoUpdated = platoService.updatePlato(id, platoDetalles);
            return new ResponseEntity<>(platoUpdated, HttpStatus.OK);
        } catch (PlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //-----PUT----- APROBAR PLATO
    @Operation(summary = "Aprueba un plato por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Plato aprobado", content = @Content(schema = @Schema(implementation = Plato.class))), @ApiResponse(responseCode = "404", description = "Plato o aprobador no encontrado", content = @Content)})
    @PutMapping("/aprobar/{platoId}/{aprobadorId}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<Plato> aprobarPlato(@PathVariable Long platoId, @PathVariable Long aprobadorId) {
        try {
            Plato platoAprobado = platoService.aprobarPlato(platoId, aprobadorId);
            return new ResponseEntity<>(platoAprobado, HttpStatus.OK);
        } catch (PlatoNotFoundException | UsuarioNotFoundException | RolNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //-----PUT----- BORRAR LOGICAMENTE PLATO
    @Operation(summary = "Realiza el borrado lógico de un plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato marcado como eliminado"),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado")
    })
    @PutMapping("/borrar-logico/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<String> borrarLogico(@PathVariable Long id) {
        try {
            platoService.borrarLogico(id);
            return ResponseEntity.ok("Plato eliminado lógicamente con éxito");
        } catch (PlatoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //-----DELETE----- ELIMINAR PLATO -----NO RECOMENDADO
    @Operation(summary = "Elimina un plato por su ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Plato eliminado", content = @Content), @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)})
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Response> deletePlato(@PathVariable Long id) {
        try {
            platoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PlatoNotFoundException e) {
            return handleException(e);
        }
    }


    // Manejo de excepciones
    @ExceptionHandler(PlatoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(PlatoNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
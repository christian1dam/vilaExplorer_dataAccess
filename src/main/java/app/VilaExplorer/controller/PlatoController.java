package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.service.PlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.event.ItemListener;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la API REST de Platos.
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato encontrado", content = @Content(schema = @Schema(implementation = Plato.class))),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Plato> getPlatoById(@PathVariable Long id) {
        Optional<Plato> plato = platoService.findById(id);
        return plato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtiene todos los platos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de platos", content = @Content(schema = @Schema(implementation = Plato.class)))
    })
    @GetMapping("/todos")
    public ResponseEntity<Iterable<Plato>> getAllPlatos() {
       Iterable<Plato> platos = platoService.findAll();
        return new ResponseEntity<>(platos, HttpStatus.OK);
    }

    @Operation(summary = "Crea un nuevo plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato creado", content = @Content(schema = @Schema(implementation = Plato.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados invalidos", content = @Content)
    })
    @PostMapping("/crear")
    public ResponseEntity<Plato> createPlato(@RequestBody Plato plato) {
        return new ResponseEntity<>(platoService.save(plato), HttpStatus.OK);
    }


    // actualizar un plato existente
    @Operation(summary = "Modifica un plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato modificado", content = @Content(schema = @Schema(implementation = Plato.class))),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Plato> updatePlato(@PathVariable Long id, @RequestBody Plato platoDetalles) {
        Plato plato = platoService.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el plato con el id: " + id));

        plato.setNombre(platoDetalles.getNombre());
        plato.setDescripcion(platoDetalles.getDescripcion());
        plato.setIngredientes(platoDetalles.getIngredientes());
        plato.setReceta(platoDetalles.getReceta());
        plato.setTipoPlato(platoDetalles.getTipoPlato());
        plato.setAutor(platoDetalles.getAutor());

        return ResponseEntity.ok(platoService.save(plato));
    }


    @Operation(summary = "Elimina un plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plato eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Plato no encontrado", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deletePlato(@PathVariable Long id) {
        platoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Aprueba un plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato aprobado", content = @Content(schema = @Schema(implementation = Plato.class))),
            @ApiResponse(responseCode = "404", description = "Plato o aprobador no encontrado", content = @Content)
    })
    @PutMapping("/aprobar/{platoId}/{aprobadorId}")
    public ResponseEntity<Plato> aprobarPlato(@PathVariable Long platoId, @PathVariable Long aprobadorId) {
        Plato platoAprobado = platoService.aprobarPlato(platoId, aprobadorId);
        return ResponseEntity.ok(platoAprobado);
    }

}
package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Ruta;
import app.VilaExplorer.service.RutaService;
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
 * Controlador para la API REST de Rutas.
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@RestController
@RequestMapping("/ruta")
public class RutaController {

    @Autowired
    private RutaService rutaService;


    @Operation(summary = "Obtiene una ruta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta encontrada", content = @Content(schema = @Schema(implementation = Ruta.class))),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Ruta> getRutaById(@PathVariable Long id) {
        Optional<Ruta> ruta = rutaService.findById(id);
        return ruta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Obtiene todas las rutas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de rutas", content = @Content(schema = @Schema(implementation = Ruta.class)))
    })
    @GetMapping("/todos")
    public List<Ruta> getAllRutas() {
        return rutaService.findAll();
    }



    @Operation(summary = "Crea una nueva ruta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta creada", content = @Content(schema = @Schema(implementation = Ruta.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados invalidos", content = @Content)
    })
    @PostMapping("/crear")
    public ResponseEntity<Ruta> createRuta(@RequestBody Ruta ruta) {
        // Validar que se hayan enviado coordenadas
        if (ruta.getCoordenadas() == null || ruta.getCoordenadas().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Es obligatorio tener al menos un par de coordenadas
        }

        // Asociar cada coordenada a la ruta
        ruta.getCoordenadas().forEach(coordenada -> coordenada.setRuta(ruta));

        // Guardar la ruta junto con las coordenadas asociadas
        Ruta savedRuta = rutaService.save(ruta);
        return ResponseEntity.ok(savedRuta);
    }


    @Operation(summary = "Elimina una ruta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ruta eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable Long id) {
        rutaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener rutas por autor
    @Operation(summary = "Obtiene rutas por autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutas encontradas", content = @Content(schema = @Schema(implementation = Ruta.class)))
    })
    @GetMapping("/autor/{autorId}")
    public List<Ruta> getRutasByAutor(@PathVariable Long autorId) {
        return rutaService.findByAutorId(autorId);
    }
}

package app.VilaExplorer.controller;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.service.FiestaTradicionService;
import app.VilaExplorer.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador de fiestas tradicionales.
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@RestController
@Tag(name = "Fiestas", description = "API para la gestión de fiestas tradicionales")
@RequestMapping("/api/fiestas")
public class FiestaTradicionController {

    @Autowired
    private FiestaTradicionService fiestaTradicionService;

    @Autowired
    private UsuarioService usuarioService; // Se inyecta el UsuarioService para buscar el objeto Usuario


    @Operation(summary = "Obtener una fiesta tradicional por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiesta tradicional encontrada", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "404", description = "Fiesta tradicional no encontrada", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    public ResponseEntity<FiestaTradicion> getFiestaTradicionById(@PathVariable Long idFiestaTradicion) {
        Optional<FiestaTradicion> fiestaTradicion = fiestaTradicionService.findById(idFiestaTradicion);
        return fiestaTradicion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Obtener todas las fiestas tradicionales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiestas tradicionales encontradas", content = @Content(schema = @Schema(implementation = FiestaTradicion.class)))
    })
    @GetMapping("/todos")
    public List<FiestaTradicion> getAllFiestasTradicion() {
        return fiestaTradicionService.findAll();
    }


    @Operation(summary = "Crear una fiesta tradicional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiesta tradicional creada", content = @Content(schema = @Schema(implementation = FiestaTradicion.class)))
    })
    @PostMapping("/crear")
    public FiestaTradicion createFiestaTradicion(@RequestBody FiestaTradicion fiestaTradicion) {
        return fiestaTradicionService.save(fiestaTradicion);
    }


    @Operation(summary = "Modificar una fiesta tradicional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiesta tradicional modificada", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "404", description = "Fiesta tradicional no encontrada", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    public ResponseEntity<FiestaTradicion> updateFiestaTradicion(@PathVariable Long id, @RequestBody FiestaTradicion fiestaTradicion) {
        Optional<FiestaTradicion> existingFiestaTradicion = fiestaTradicionService.findById(id);
        if (existingFiestaTradicion.isPresent()) {
            fiestaTradicion.setIdFiestaTradicion(id);
            return ResponseEntity.ok(fiestaTradicionService.save(fiestaTradicion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @Operation(summary = "Eliminar una fiesta tradicional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fiesta tradicional eliminada", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteFiestaTradicion(@PathVariable Long id) {
        fiestaTradicionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "Obtener todas las fiestas tradicionales de un autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de fiestas tradicionales del autor", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content)
    })
    @GetMapping("/autor/{idAutor}")
    public ResponseEntity<List<FiestaTradicion>> getFiestasByAutor(@PathVariable Long idAutor) {
        Optional<Usuario> autor = usuarioService.findById(idAutor);
        if (autor.isPresent()) {
            List<FiestaTradicion> fiestas = fiestaTradicionService.findByAutor(autor.get());
            return ResponseEntity.ok(fiestas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Buscar fiestas tradicionales por palabra clave")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiestas tradicionales encontradas", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron fiestas tradicionales", content = @Content)
    })
    @GetMapping("/buscar_palabra")
    public ResponseEntity<List<FiestaTradicion>> searchFiestas(@RequestParam String keyword) {
        List<FiestaTradicion> results = fiestaTradicionService.searchByKeyword(keyword);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(results);
        }
    }



    @Operation(summary = "Buscar fiestas tradicionales por palabra clave con paginacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiestas tradicionales encontradas", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron fiestas tradicionales", content = @Content)
    })
    @GetMapping("/buscar")
    public ResponseEntity<Page<FiestaTradicion>> searchFiestas(@RequestParam String keyword, Pageable pageable) {
        Page<FiestaTradicion> results = fiestaTradicionService.searchByKeyword(keyword, pageable);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(results);
        }
    }


}
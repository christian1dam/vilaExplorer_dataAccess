package app.VilaExplorer.controller;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.exception.FiestaTradicionNotFound;
import app.VilaExplorer.exception.UsuarioNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

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

    // Buscar fiestas tradicionales activas
    @GetMapping("/activas")
    public List<FiestaTradicion> getAllFiestasTradicionActivas() {
        return fiestaTradicionService.findAllActive();
    }

    // Buscar fiestas tradicionales activas por palabra clave
    @GetMapping("/buscar_activos")
    public ResponseEntity<List<FiestaTradicion>> searchFiestasActivas(@RequestParam String keyword) {
        List<FiestaTradicion> results = fiestaTradicionService.searchActiveByKeyword(keyword);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(results);
        }
    }

    // Buscar fiestas tradicionales activas por palabra clave con paginacion
    @GetMapping("/buscar_activos_paginados")
    public ResponseEntity<Page<FiestaTradicion>> searchFiestasActivasPaginadas(@RequestParam String keyword, Pageable pageable) {
        Page<FiestaTradicion> results = fiestaTradicionService.searchActiveByKeyword(keyword, pageable);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(results);
        }
    }

    // Buscar fiestas tradicionales activas de un autor
    @GetMapping("/activas/autor/{idAutor}")
    public ResponseEntity<List<FiestaTradicion>> getFiestasActivasByAutor(@PathVariable Long idAutor) {
        List<FiestaTradicion> fiestas = fiestaTradicionService.findActiveByAutor(idAutor);
        if (fiestas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fiestas);
    }



    @Operation(summary = "Obtener una fiesta tradicional por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiesta tradicional encontrada", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "404", description = "Fiesta tradicional no encontrada", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    public ResponseEntity<FiestaTradicion> getFiestaTradicionById(@PathVariable Long id) {
        try {
            FiestaTradicion fiesta = fiestaTradicionService.findById(id);
            return new ResponseEntity<>(fiesta, HttpStatus.OK);
        } catch (FiestaTradicionNotFound e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<FiestaTradicion> createFiestaTradicion(@RequestBody FiestaTradicion fiestaTradicion, @RequestParam(value = "autor") Long idAutor) {
        try {
            FiestaTradicion fiestaCreada = fiestaTradicionService.save(fiestaTradicion, idAutor);
            return new ResponseEntity<>(fiestaCreada, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modificar una fiesta tradicional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiesta tradicional modificada", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "404", description = "Fiesta tradicional no encontrada", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    public ResponseEntity<FiestaTradicion> updateFiestaTradicion(@PathVariable Long id, @RequestBody FiestaTradicion fiestaTradicion) {
        try {
            FiestaTradicion fiestaActualizada = fiestaTradicionService.updateFiestaTradicion(id, fiestaTradicion);
            return new ResponseEntity<>(fiestaActualizada, HttpStatus.OK);
        } catch (FiestaTradicionNotFound e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // Eliminar una fiesta tradicional de forma física
    @Operation(summary = "Eliminar una fiesta tradicional")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fiesta tradicional eliminada", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Response> deleteFiestaTradicion(@PathVariable Long id) {
        try {
            fiestaTradicionService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FiestaTradicionNotFound e) {
            return handleException(e);
        }
    }


    // Eliminar una fiesta tradicional de forma lógica
    @DeleteMapping("/eliminar/logico/{id}")
    public ResponseEntity<Void> deleteFiestaTradicionLogico(@PathVariable Long id) {
        try {
            fiestaTradicionService.deleteLogicallyById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }



    @Operation(summary = "Obtener todas las fiestas tradicionales de un autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de fiestas tradicionales del autor", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content)
    })
    @GetMapping("/autor/{idAutor}")
    public ResponseEntity<List<FiestaTradicion>> getFiestasByAutor(@PathVariable Long idAutor) {
        try {
            List<FiestaTradicion> fiestasPorAutor = fiestaTradicionService.getFiestaByAutor(idAutor);
            return new ResponseEntity<>(fiestasPorAutor, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Buscar fiestas tradicionales por palabra clave")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiestas tradicionales encontradas", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron fiestas tradicionales", content = @Content)
    })
    @GetMapping("/buscar_palabra")
    public ResponseEntity<List<FiestaTradicion>> searchFiestas(@RequestParam String keyword) {
        try {
            List<FiestaTradicion> results = fiestaTradicionService.searchByKeyword(keyword);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (FiestaTradicionNotFound e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @Operation(summary = "Buscar fiestas tradicionales por palabra clave con paginacion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fiestas tradicionales encontradas", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron fiestas tradicionales", content = @Content)
    })
    @GetMapping("/buscar")
    public ResponseEntity<Page<FiestaTradicion>> searchFiestas(@RequestParam String keyword, Pageable pageable) {
        try {
            Page<FiestaTradicion> results = fiestaTradicionService.searchByKeyword(keyword, pageable);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (FiestaTradicionNotFound e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(FiestaTradicionNotFound.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(FiestaTradicionNotFound ftnf) {
        Response response = Response.errorResponse(NOT_FOUND,
                ftnf.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
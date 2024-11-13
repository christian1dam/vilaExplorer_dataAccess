package app.VilaExplorer.controller;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.exception.FiestaTradicionNotFound;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.service.FiestaTradicionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

/**
 * Controlador de fiestas tradicionales.
 *
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@RestController
@Tag(name = "Fiestas", description = "API para la gestión de fiestas tradicionales")
@RequestMapping("/api/fiestas")
public class FiestaTradicionController {

    @Autowired
    private FiestaTradicionService fiestaTradicionService;

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
        } catch(DataIntegrityViolationException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
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


    @Operation(summary = "Obtener todas las fiestas tradicionales de un autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de fiestas tradicionales del autor", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado", content = @Content)
    })
    @GetMapping("/autor/{idAutor}")
    public ResponseEntity<List<FiestaTradicion>> getFiestasByAutor(@PathVariable Long idAutor) {
        try {
            List<FiestaTradicion> fiestasPorAutor = fiestaTradicionService.getListaFiestasByAutor(idAutor);
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


    @Operation(summary = "Buscar fiestas tradicionales por palabra clave con paginación")
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
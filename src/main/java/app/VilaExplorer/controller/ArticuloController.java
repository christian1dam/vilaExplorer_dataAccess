package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Articulo;
import app.VilaExplorer.exception.ArticuloNotFoundException;
import app.VilaExplorer.service.ArticuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

/**
 * Controlador que permite gestional los articulos redactados
 *
 * @Author vilaExplorerAdmin
 * @Version 1.0
 */

@RestController
@Tag(name = "Articulos", description = "API para la gestión de articulos")
@RequestMapping("/api/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @Operation(summary = "Obtener un articulo por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Articulo encontrado"),
            @ApiResponse(responseCode = "404", description = "Articulo no encontrado")
    })
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Articulo> getArticuloById(@PathVariable Long id) {
        try {
            Articulo articulo = articuloService.findById(id);
            return new ResponseEntity<>(articulo, HttpStatus.OK);
        } catch (ArticuloNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener todos los articulos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Articulos encontrados"),
            @ApiResponse(responseCode = "404", description = "Articulos no encontrados")
    })
    @GetMapping("/todos")
    public ResponseEntity<List<Articulo>> getAllArticulos() {
        try {
            List<Articulo> articulos = articuloService.findAll();
            return new ResponseEntity<>(articulos, HttpStatus.OK);
        } catch (ArticuloNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear un articulo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Articulo creado", content = @Content(schema = @Schema(implementation = Articulo.class)))
    })
    @PostMapping("/crear")
    public Articulo createArticulo(@RequestBody Articulo articulo) {
        return articuloService.save(articulo);
    }


    @Operation(summary = "Elimina un articulo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Articulo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Articulo no encontrado", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Response> deleteArticulo(@PathVariable Long id) {
        try {
            articuloService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ArticuloNotFoundException e) {
            return handleException(e);
        }
    }

    @ExceptionHandler(ArticuloNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ArticuloNotFoundException anfe) {
        Response response = Response.errorResponse(NOT_FOUND,
                anfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

package app.VilaExplorer.controller;

import app.VilaExplorer.domain.CategoriaPlato;
import app.VilaExplorer.exception.CategoriaPlatoNotFoundException;
import app.VilaExplorer.service.CategoriaPlatoService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

/**
 * Controlador de categorías de plato
 *
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@RestController
@Tag(name = "Categorias de plato", description = "API para la gestión de categorías de plato")
    @RequestMapping("categoria_plato")
public class CategoriaPlatoController {

    @Autowired
    private CategoriaPlatoService categoriaPlatoService;


    @Operation(summary = "Obtiene una categoría de plato por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria de plato encontrada", content = @Content(schema = @Schema(implementation = CategoriaPlato.class))),
            @ApiResponse(responseCode = "404", description = "Categoria de plato no encontrada", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    public ResponseEntity<CategoriaPlato> getCategoriaPlatoById(@PathVariable Long id) {
        try {
            CategoriaPlato categoriaPlato = categoriaPlatoService.findById(id);
            return new ResponseEntity<>(categoriaPlato, HttpStatus.OK);
        } catch (CategoriaPlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Obtiene todas las categorias de plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de categorias de plato", content = @Content(schema = @Schema(implementation = CategoriaPlato.class)))
    })
    @GetMapping("/todos")
    public List<CategoriaPlato> getAllCategoriasPlato() {
        return categoriaPlatoService.findAll();
    }


    @Operation(summary = "Obtiene todas las categorias de plato activas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de categorias de plato activas", content = @Content(schema = @Schema(implementation = CategoriaPlato.class)))
    })
    @GetMapping("/activos")
    public List<CategoriaPlato> getAllCategoriasPlatoActivos() {
        return categoriaPlatoService.findAllActivos();
    }


    @Operation(summary = "Crea una nueva categoría de plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria de plato creada", content = @Content(schema = @Schema(implementation = CategoriaPlato.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto: el objeto ya existe en la base de datos", content = @Content(schema = @Schema(implementation = CategoriaPlato.class)))
    })
    @PostMapping("/crear")
    public ResponseEntity<CategoriaPlato> createCategoriaPlato(@RequestBody CategoriaPlato categoriaPlato) {
        try {
            CategoriaPlato nueva = categoriaPlatoService.crearCategoriaPlato(categoriaPlato);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Modifica una categoria de plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria de plato modificada", content = @Content(schema = @Schema(implementation = CategoriaPlato.class))),
            @ApiResponse(responseCode = "404", description = "Categoria de plato no encontrada", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    public ResponseEntity<CategoriaPlato> updateCategoriaPlato(@PathVariable Long id, @RequestBody CategoriaPlato categoriaPlato) {
        try {
            CategoriaPlato categoriaActualizada = categoriaPlatoService.updateCategoriaPlato(id, categoriaPlato);
            return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);
        } catch (CategoriaPlatoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Borrado lógico de una categoría de plato por ID
    //Para mantener la integridad de los datos, se realiza un borrado lógico
    @Operation(summary = "Elimina una categoria de plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria de plato eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoria de plato no encontrada", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Response> deleteCategoriaPlatoLogico(@PathVariable Long id) {
        try {
            categoriaPlatoService.deleteByIdLogico(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CategoriaPlatoNotFoundException e) {
            return handleException(e);
        }
    }

    @ExceptionHandler(CategoriaPlatoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(CategoriaPlatoNotFoundException cpnfe) {
        Response response = Response.errorResponse(NOT_FOUND,
                cpnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
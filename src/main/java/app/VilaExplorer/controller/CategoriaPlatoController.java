package app.VilaExplorer.controller;

import app.VilaExplorer.domain.CategoriaPlato;
import app.VilaExplorer.service.CategoriaPlatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador de categorías de plato
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@RestController
@Tag(name = "Categorias de plato", description = "API para la gestión de categorías de plato")
@RequestMapping("/api/categorias-plato")
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
        Optional<CategoriaPlato> categoriaPlato = categoriaPlatoService.findById(id);
        return categoriaPlato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
            @ApiResponse(responseCode = "200", description = "Categoria de plato creada", content = @Content(schema = @Schema(implementation = CategoriaPlato.class)))
    })
    @PostMapping("/crear")
    public CategoriaPlato createCategoriaPlato(@RequestBody CategoriaPlato categoriaPlato) {
        return categoriaPlatoService.save(categoriaPlato);
    }


    @Operation(summary = "Modifica una categoria de plato por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria de plato modificada", content = @Content(schema = @Schema(implementation = CategoriaPlato.class))),
            @ApiResponse(responseCode = "404", description = "Categoria de plato no encontrada", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    public ResponseEntity<CategoriaPlato> updateCategoriaPlato(@PathVariable Long id, @RequestBody CategoriaPlato categoriaPlato) {
        Optional<CategoriaPlato> existingCategoriaPlato = categoriaPlatoService.findById(id);
        if (existingCategoriaPlato.isPresent()) {
            categoriaPlato.setIdCategoriaPlato(id); // Actualiza el campo 'idCategoria'
            return ResponseEntity.ok(categoriaPlatoService.save(categoriaPlato));
        } else {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<Void> deleteCategoriaPlatoLogico(@PathVariable Long id) {
        categoriaPlatoService.deleteByIdLogico(id);
        return ResponseEntity.noContent().build();
    }
}
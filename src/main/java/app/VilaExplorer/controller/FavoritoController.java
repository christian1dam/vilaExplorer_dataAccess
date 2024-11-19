package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.exception.CategoriaPlatoNotFoundException;
import app.VilaExplorer.exception.FavoritoNotFoundException;
import app.VilaExplorer.service.FavoritoService;
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
 * Controlador de favoritos.
 *
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@RestController
@Tag(name = "Favoritos", description = "API para la gestión de favoritos")
@RequestMapping("/favorito")
public class FavoritoController {


    @Autowired
    private FavoritoService favoritoService;


    @Operation(summary = "Obtener un favorito por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorito encontrado", content = @Content(schema = @Schema(implementation = Favorito.class))),
            @ApiResponse(responseCode = "404", description = "Favorito no encontrado", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Favorito> getFavoritoById(@PathVariable Long id) {
        try {
            Favorito favorito = favoritoService.findById(id);
            return new ResponseEntity<>(favorito, HttpStatus.OK);
        } catch (FavoritoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Obtener todos los favoritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favoritos encontrados", content = @Content(schema = @Schema(implementation = Favorito.class)))
    })
    @GetMapping("/todos")
    public List<Favorito> getAllFavoritos() {
        return favoritoService.findAll();
    }


    @Operation(summary = "Obtener favoritos por usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favoritos encontrados", content = @Content(schema = @Schema(implementation = Favorito.class)))
    })
    @GetMapping("/usuario/{idUsuario}")
    public List<Favorito> getFavoritosByUsuario(@PathVariable Long idUsuario) {
        return favoritoService.findByUsuario_IdUsuario(idUsuario);
    }


    @Operation(summary = "Obtener favoritos por usuario y tipo de entidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favoritos encontrados", content = @Content(schema = @Schema(implementation = Favorito.class)))
    })
    @GetMapping("/usuario/{idUsuario}/tipo/{tipoEntidad}")
    public List<Favorito> getFavoritosByUsuarioAndTipoEntidad(@PathVariable Long idUsuario, @PathVariable TipoEntidad tipoEntidad) {
        return favoritoService.findByUsuario_IdUsuarioAndTipoEntidad(idUsuario, tipoEntidad);
    }


    @Operation(summary = "Crear un favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorito creado", content = @Content(schema = @Schema(implementation = Favorito.class)))
    })
    @PostMapping("/crear")
    public Favorito createFavorito(@RequestBody Favorito favorito) {
        return favoritoService.save(favorito);
    }


    @Operation(summary = "Modificar un favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorito modificado", content = @Content(schema = @Schema(implementation = Favorito.class))),
            @ApiResponse(responseCode = "404", description = "Favorito no encontrado", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Favorito> updateFavorito(@PathVariable Long id, @RequestBody Favorito favorito) {
        try {
            Favorito actualizado = favoritoService.updateFavorito(id, favorito);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (FavoritoNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Eliminar un favorito por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorito eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Favorito no encontrado", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Response> deleteFavorito(@PathVariable Long id) {
        try {
            favoritoService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FavoritoNotFoundException e) {
            return handleException(e);
        }
    }

    @ExceptionHandler(FavoritoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(FavoritoNotFoundException fnfe) {
        Response response = Response.errorResponse(NOT_FOUND,
                fnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

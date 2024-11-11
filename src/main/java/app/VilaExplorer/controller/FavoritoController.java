package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.service.FavoritoService;
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
 * Controlador de favoritos.
 * @Author VilaExplorerAdmin
 * @Version 1.0
 */
@RestController
@Tag(name = "Favoritos", description = "API para la gestión de favoritos")
@RequestMapping("/api/favoritos")
public class FavoritoController {


    @Autowired
    private FavoritoService favoritoService;


    @Operation(summary = "Obtener un favorito por su id")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Favorito encontrado", content = @Content(schema = @Schema(implementation = Favorito.class))),
            @ApiResponse(responseCode = "404", description = "Favorito no encontrado", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Favorito> getFavoritoById(@PathVariable Long id) {
        Optional<Favorito> favorito = favoritoService.findById(id);
        return favorito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @Operation(summary = "Obtener todos los favoritos")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Favoritos encontrados", content = @Content(schema = @Schema(implementation = Favorito.class)))
    })
    @GetMapping("/todos")
    public List<Favorito> getAllFavoritos() {
        return favoritoService.findAll();
    }


    @Operation(summary = "Obtener favoritos por usuario")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Favoritos encontrados", content = @Content(schema = @Schema(implementation = Favorito.class)))
    })
    @GetMapping("/usuario/{idUsuario}")
    public List<Favorito> getFavoritosByUsuario(@PathVariable Long idUsuario) {
        return favoritoService.findByUsuario_IdUsuario(idUsuario);
    }


    @Operation(summary = "Obtener favoritos por usuario y tipo de entidad")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Favoritos encontrados", content = @Content(schema = @Schema(implementation = Favorito.class)))
    })
    @GetMapping("/usuario/{idUsuario}/tipo/{tipoEntidad}")
    public List<Favorito> getFavoritosByUsuarioAndTipoEntidad(@PathVariable Long idUsuario, @PathVariable TipoEntidad tipoEntidad) {
        return favoritoService.findByUsuario_IdUsuarioAndTipoEntidad(idUsuario, tipoEntidad);
    }


    @Operation(summary = "Crear un favorito")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Favorito creado", content = @Content(schema = @Schema(implementation = Favorito.class)))
    })
    @PostMapping("/crear")
    public Favorito createFavorito(@RequestBody Favorito favorito) {
        return favoritoService.save(favorito);
    }


    @Operation(summary = "Modificar un favorito")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "200", description = "Favorito modificado", content = @Content(schema = @Schema(implementation = Favorito.class))),
            @ApiResponse(responseCode = "404", description = "Favorito no encontrado", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Favorito> updateFavorito(@PathVariable Long id, @RequestBody Favorito favorito) {
        Optional<Favorito> existingFavorito = favoritoService.findById(id);
        if (existingFavorito.isPresent()) {
            favorito.setIdFavorito(id);
            return ResponseEntity.ok(favoritoService.save(favorito));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Eliminar un favorito por su id")
    @ApiResponses (value = {
            @ApiResponse(responseCode = "204", description = "Favorito eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Favorito no encontrado", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long id) {
        favoritoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

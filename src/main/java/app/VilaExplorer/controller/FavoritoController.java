package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.LugarInteres;
import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.enums.TipoEntidad;
import app.VilaExplorer.exception.FavoritoNotFoundException;
import app.VilaExplorer.exception.FiestaTradicionNotFound;
import app.VilaExplorer.exception.LugarInteresNotFoundException;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.service.FavoritoService;
import app.VilaExplorer.service.FiestaTradicionService;
import app.VilaExplorer.service.LugarInteresService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private LugarInteresService lugarInteresService;
    @Autowired
    private FiestaTradicionService fiestaTradicionService;
    @Autowired
    private PlatoService platoService;


    @Operation(summary = "Obtener un favorito por su id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Favorito encontrado", content = @Content(schema = @Schema(implementation = Favorito.class))), @ApiResponse(responseCode = "404", description = "Favorito no encontrado", content = @Content)})
    @GetMapping("/detalle/{id}")
    @PreAuthorize("hasRole('Cliente') or hasRole('Administrador')")
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
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Favoritos encontrados", content = @Content(schema = @Schema(implementation = Favorito.class)))})
    @GetMapping("/todos")
    @PreAuthorize("hasRole('Administrador')")
    public List<Favorito> getAllFavoritos() {
        return favoritoService.findAll();
    }


    @Operation(summary = "Obtener favoritos por usuario")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Favoritos encontrados", content = @Content(schema = @Schema(implementation = Favorito.class)))})
    @GetMapping("/usuario/{idUsuario}")
    @PreAuthorize("hasRole('Cliente') or hasRole('Administrador')")
    public ResponseEntity<List<Object>> getFavoritosByUsuario(@PathVariable Long idUsuario) {
        List<Favorito> favoritosDelUsuario = favoritoService.findByUsuario_IdUsuario(idUsuario);
        List<Object> favortios = new ArrayList<>();
        try {
            for (Favorito favorito : favoritosDelUsuario) {
                if (TipoEntidad.FIESTA_TRADICION == favorito.getTipoEntidad()) {
                    FiestaTradicion fiestaFavoritaUsuario = fiestaTradicionService.findById(favorito.getIdEntidad());
                    favortios.add(fiestaFavoritaUsuario);
                } else if (TipoEntidad.PLATO == favorito.getTipoEntidad()) {
                    Plato platoFavoritoUsuario = platoService.findById(favorito.getIdEntidad());
                    favortios.add(platoFavoritoUsuario);
                } else if (TipoEntidad.LUGAR_INTERES == favorito.getTipoEntidad()) {
                    LugarInteres lugarInteresFavoritoUsuario = lugarInteresService.findById(favorito.getIdEntidad());
                    favortios.add(lugarInteresFavoritoUsuario);
                }
            }
            return new ResponseEntity<>(favortios, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Obtener favoritos por usuario y tipo de entidad")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Favoritos encontrados", content = @Content(schema = @Schema(implementation = Favorito.class)))})
    @GetMapping("/usuario/{idUsuario}/tipo/{tipoEntidad}")
    @PreAuthorize("hasRole('Cliente') or hasRole('Administrador')")
    public List<Favorito> getFavoritosByUsuarioAndTipoEntidad(@PathVariable Long idUsuario, @PathVariable TipoEntidad tipoEntidad) {
        return favoritoService.findByUsuario_IdUsuarioAndTipoEntidad(idUsuario, tipoEntidad);
    }


    @Operation(summary = "Crear un favorito")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Favorito creado", content = @Content(schema = @Schema(implementation = Favorito.class)))})
    @PostMapping("/crear")
    @PreAuthorize("hasRole('Cliente') or hasRole('Administrador')")
    public ResponseEntity<Object> createFavorito(@RequestBody Favorito favorito) {
         Object favoritoCreado = null;
        try {
            Favorito fav = favoritoService.save(favorito);

            if(fav == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            if (TipoEntidad.FIESTA_TRADICION == fav.getTipoEntidad()) {
                favoritoCreado = fiestaTradicionService.findById(fav.getIdEntidad());
            } else if (TipoEntidad.PLATO == fav.getTipoEntidad()) {
                favoritoCreado = platoService.findById(fav.getIdEntidad());
            } else if (TipoEntidad.LUGAR_INTERES == fav.getTipoEntidad()) {
                favoritoCreado = lugarInteresService.findById(favorito.getIdEntidad());
            }

            return new ResponseEntity<>(favoritoCreado, HttpStatus.OK);

        } catch (FiestaTradicionNotFound | PlatoNotFoundException | LugarInteresNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Modificar un favorito")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Favorito modificado", content = @Content(schema = @Schema(implementation = Favorito.class))), @ApiResponse(responseCode = "404", description = "Favorito no encontrado", content = @Content)})
    @PutMapping("/modificar/{id}")
    @PreAuthorize("hasRole('Cliente') or hasRole('Administrador')")
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
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Favorito eliminado", content = @Content), @ApiResponse(responseCode = "404", description = "Favorito no encontrado", content = @Content)})
    @DeleteMapping("/eliminar")
    @PreAuthorize("hasRole('Cliente') or hasRole('Administrador')")
    public ResponseEntity<Response> deleteFavorito(@RequestParam Long idEntidad, @RequestParam Long idUsuario) {
        try {
            favoritoService.deleteByIdEntidad(idEntidad, idUsuario);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FavoritoNotFoundException e) {
            return handleException(e);
        }
    }

    @ExceptionHandler(FavoritoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(FavoritoNotFoundException fnfe) {
        Response response = Response.errorResponse(NOT_FOUND, fnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

package app.VilaExplorer.controller;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.LugarInteres;
import app.VilaExplorer.exception.CoordenadasNotFoundException;
import app.VilaExplorer.exception.LugarInteresNotActiveException;
import app.VilaExplorer.exception.LugarInteresNotFoundException;
import app.VilaExplorer.service.LugarInteresService;
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

import java.util.List;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

/**
 * Controlador para la API REST de Lugares de Interes.
 *
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@RestController
@Tag(name = "Lugares de Interes", description = "API para la gestion de lugares de interes del sistema")
@RequestMapping("/lugar_interes")
public class LugarInteresController {

    @Autowired
    private LugarInteresService lugarInteresService;

    @Operation(summary = "Obtiene un lugar de interes por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes encontrado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content)
    })
    @GetMapping("/detalle-completo/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor'))")
    public ResponseEntity<LugarInteres> getLugarInteresById(@PathVariable Long id) {
        try {
            LugarInteres lugarInteres = lugarInteresService.findById(id);
            return new ResponseEntity<>(lugarInteres, HttpStatus.OK);
        } catch (LugarInteresNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Obtiene un lugar de interes activo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes activo encontrado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Lugar de interes no esta activo", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor') or hasRole('Cliente')")
    public ResponseEntity<LugarInteres> getLugarInteresActivoById(@PathVariable Long id) {
        try {
            LugarInteres lugarInteres = lugarInteresService.findLugarInteresActivoByID(id);
            return new ResponseEntity<>(lugarInteres, HttpStatus.OK);
        } catch (LugarInteresNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (LugarInteresNotActiveException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Obtiene todos los lugares de interes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de lugares de interes", content = @Content(schema = @Schema(implementation = LugarInteres.class)))
    })
    @GetMapping("/todos")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<List<LugarInteres>> getAllLugaresInteres() {
        try {
            List<LugarInteres> lugaresInteres = lugarInteresService.findAll();
            return new ResponseEntity<>(lugaresInteres, HttpStatus.OK);
        } catch (LugarInteresNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Obtiene todos los lugares de interes activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de lugares de interes activos", content = @Content(schema = @Schema(implementation = LugarInteres.class)))
    })
    @GetMapping("/activos")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor') or hasRole('Cliente')")
    public ResponseEntity<List<LugarInteres>> getAllLugaresInteresActivos() {
        try {
            List<LugarInteres> lugaresInteresActivos = lugarInteresService.findAllActivos();
            return new ResponseEntity<>(lugaresInteresActivos, HttpStatus.OK);
        } catch (LugarInteresNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Crea un nuevo lugar de interes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes creado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no creado", content = @Content)
    })
    @PostMapping("/crear")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
//  Aqui cambio el ResponseEntity<LugarInteres> por ResponseEntity<?> para poder devolver tanto el objeto JSON LugarInteres como la Response con la excepcion.
    public ResponseEntity<?> createLugarInteres(@RequestBody LugarInteres lugarInteres) {
        try {
            LugarInteres lugarInteresCreado = lugarInteresService.save(lugarInteres);
            return new ResponseEntity<>(lugarInteresCreado, HttpStatus.OK);
        } catch (CoordenadasNotFoundException e) {
            return handleException(e);
        }
    }


    @Operation(summary = "Modifica un lugar de interes por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes modificado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<LugarInteres> updateLugarInteres(@PathVariable Long id, @RequestBody LugarInteres lugarInteresDetalle) {
        try {
            LugarInteres lugarInteres = lugarInteresService.updateLugarInteres(id, lugarInteresDetalle);
            return new ResponseEntity<>(lugarInteres, HttpStatus.OK);
        } catch (LugarInteresNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //Metodo para desactivar un lugar de interes (eliminar logicamente)
    @Operation(summary = "Desactiva un lugar de interés por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes desactivado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content)
    })
    @PutMapping("/desactivar/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<LugarInteres> desactivarLugarInteres(@PathVariable Long id) {
        try {
            LugarInteres lugarInteres = lugarInteresService.desactivarLugarInteres(id);
            return new ResponseEntity<>(lugarInteres, HttpStatus.OK);
        } catch (LugarInteresNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Metodo para Activar un lugar de interes (cambiar el estado de activo a true)
    @Operation(summary = "Activa un lugar de interes por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes activado", content = @Content(schema = @Schema(implementation = LugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content)
    })
    @PutMapping("/activar/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<LugarInteres> activarLugarInteres(@PathVariable Long id) {
        try {
            LugarInteres lugarInteres = lugarInteresService.activarLugarInteres(id);
            return new ResponseEntity<>(lugarInteres, HttpStatus.OK);
        } catch (LugarInteresNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Elimina un lugar de interes de forma logica por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugar de interes eliminado logicamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lugar de interes no encontrado", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Response> deleteLugarInteres(@PathVariable Long id) {

        try {
            lugarInteresService.deleteByIdLogico(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LugarInteresNotFoundException e) {
            return handleException(e);
        }
    }

    @Operation(summary = "Buscar lugar de interés por palabra clave")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lugares de interés encontrados", content = @Content(schema = @Schema(implementation = FiestaTradicion.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron lugares de interés", content = @Content)
    })
    @GetMapping("/buscar")
    @PreAuthorize("hasRole('Cliente') or hasRole('Redactor') or hasRole('Administrador')")
    public ResponseEntity<List<LugarInteres>> searchLugarDeInteres(@RequestParam String keyword) {
        System.out.println(keyword);
        List<LugarInteres> results = lugarInteresService.searchByKeyword(keyword);
        if(results.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }


    @ExceptionHandler({LugarInteresNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(LugarInteresNotFoundException linf) {
        Response response = Response.errorResponse(NOT_FOUND,
                linf.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CoordenadasNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(CoordenadasNotFoundException cnfe) {
        Response response = Response.errorResponse(NOT_FOUND,
                cnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
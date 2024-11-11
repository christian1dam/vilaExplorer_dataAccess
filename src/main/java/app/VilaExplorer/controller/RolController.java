package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Rol;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

/**
 * Controlador para la API REST de Roles.
 *
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/roles")
public class RolController {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";

    @Autowired
    RolService rolService;

    @Operation(summary = "Obtiene todos los roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles encontrados", content = @Content(schema = @Schema(implementation = Rol.class)))
    })
    @GetMapping("/all")
    public List<Rol> getAllRoles() {
        return rolService.getAll();
    }

    @Operation(summary = "Obtiene un rol filtrado por el nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado en la base de datos", content = @Content(schema = @Schema(implementation = Rol.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado en la base de datos", content = @Content(schema = @Schema(implementation = Rol.class))),
    })
    @GetMapping()
    public ResponseEntity<?> getRolByNombre(@RequestParam(value = "rol") String rol){
        try {
            Rol rolFromDB = rolService.getRolByNombre(rol);
            return new ResponseEntity<>(rolFromDB, HttpStatus.OK);
        } catch (RolNotFoundException e) {
             return handleException(e);
        }
    }


    @Operation(summary = "Añade un nuevo rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado", content = @Content(schema = @Schema(implementation = Rol.class)))
    })
    @PostMapping("/add")
    @Transactional
    public ResponseEntity<Rol> anyadirRol(@RequestBody Rol rol) {
        try {
            Rol nuevoRol = rolService.anyadirRol(rol);
            return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    // Metodo para hacer un borrado lógico de un rol
    @Operation(summary = "Desactiva un rol por su ID (borrado lógico)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol desactivado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @DeleteMapping("/logical/{id}")
    public ResponseEntity<Response> eliminarRolLogicamentePorID(@PathVariable Long id) {
        try {
            rolService.desactivaRolPorID(id); // Realiza un borrado lógico
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RolNotFoundException rnfe) {
            return handleException(rnfe);
        }
    }


    // Metodo para eliminar un rol por su ID fisicamente POCO RECOMENDADO
    @Operation(summary = "Elimina un rol por su ID (borrado físico)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Rol eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> eliminarRolPorID(@PathVariable Long id) {
        try {
            rolService.eliminarRolPorID(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RolNotFoundException rnfe) {
            return handleException(rnfe);
        }
    }

    @ExceptionHandler(RolNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(RolNotFoundException rcnfe) {
        Response response = Response.errorResponse(NOT_FOUND,
                rcnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static app.VilaExplorer.controller.Response.NOT_FOUND;


/**
 * Controlador para la API REST de Usuarios.
 *
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@RestController
@RequestMapping("usuario")
public class UsuarioController {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @Operation(summary = "Obtiene todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('Administrador')")
    public List<Usuario> getAllUsuarios() {
        //Cuando no se manipulan los header de la respuesta no es necesario devolver un ResponseEntity
        return usuarioService.findAll();
    }


    // Obtener un usuario por ID
    @Operation(summary = "Obtiene un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/por-id/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Obtener un usuario por Rol
    @Operation(summary = "Obtiene usuarios por rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron usuarios con ese rol", content = @Content)
    })
    @GetMapping("/por-rol/")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<List<Usuario>> getUsuariosByRol(@RequestParam(value = "rol") String rol) {
        List<Usuario> usuarios;
        try {
            usuarios = usuarioService.findUsuariosByRol(rol);
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        } catch (RolNotFoundException e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar el nombre de un usuario
    @Operation(summary = "Actualiza el nombre de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nombre actualizado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/editar/nombre")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<Usuario> editarNombre(@RequestParam(value = "id") Long id, @RequestParam(value = "nuevoNombre") String nuevoNombre) {
        try {
            Usuario usuarioActualizado = usuarioService.editarNombre(id, nuevoNombre);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar la contraseña de un usuario
    @Operation(summary = "Actualiza la contraseña de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contraseña actualizada", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/editar/contraseña")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<Usuario> editarContrasenya(@RequestParam(value = "id") Long id,
                                                     @RequestParam(value = "contraseñaActual") String contrasenyaActual,
                                                     @RequestParam(value = "nuevaContraseña") String nuevaContrasenya) {
        try {
            Usuario usuarioActualizado = usuarioService.editarContrasenya(id, contrasenyaActual, nuevaContrasenya);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    //NO SE DEBE USAR EN PRODUCCIÓN - SOLO PARA PRUEBAS
    @GetMapping("/signIn")
    public ResponseEntity<Usuario> getUsuario(@RequestParam(value = "nombre") String nombre, @RequestParam(value = "password") String password) {
        try {
            Usuario usuarioFromDB = usuarioService.findUser(nombre, password);
            System.out.println(usuarioFromDB.getNombre());
            return new ResponseEntity<>(usuarioFromDB, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear un nuevo usuario
    @Operation(summary = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Error de consistencia de datos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/add")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario, @RequestParam(value = "rol") String rol) {
        try {
            Usuario usuarioConRol = usuarioService.crearUsuarioConRol(usuario, rol);
            return new ResponseEntity<>(usuarioConRol, HttpStatus.CREATED);
        } catch (RolNotFoundException e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(usuario, HttpStatus.NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Actualizar un usuario existente
    @Operation(summary = "Actualiza un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Redactor')")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        try {
            Usuario usuarioActualizado = usuarioService.updateUsuario(id, usuarioDetails);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar el rol de un usuario
    @Operation(summary = "Actualiza el rol de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario con rol actualizado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario o rol no encontrado", content = @Content)
    })
    @PatchMapping("/update-role")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Usuario> updateRole(@RequestParam(value = "id_usuario") Long usuarioID, @RequestParam(value = "rol") String rol) {
        try {
            Usuario usuarioActualizado = usuarioService.updateRolDelUsuario(usuarioID, rol);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } catch (UsuarioNotFoundException | RolNotFoundException e) {
            System.out.println(RED + e.getMessage() + RESET);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Borrado lógico de un usuario.
     *
     * @param id ID del usuario a desactivar.
     * @return Respuesta de desactivación del usuario.
     */
    @Operation(summary = "Desactiva un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario desactivado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/desactivar/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Response> deleteUsuarioLogico(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuarioLogico(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            return handleException(e);
        }
    }


    // Eliminar un usuario permanente
    @Operation(summary = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Response> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsuarioNotFoundException e) {
            return handleException(e);
        }
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(UsuarioNotFoundException unfe) {
        Response response = Response.errorResponse(NOT_FOUND,
                unfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

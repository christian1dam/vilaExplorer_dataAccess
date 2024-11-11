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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Controlador para la API REST de Usuarios.
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @Operation(summary = "Obtiene todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @GetMapping("/getAll")
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
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario;
        usuario = usuarioService.findById(id);
        if (usuario.isPresent()) return new ResponseEntity<>(usuario, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // Obtener un usuario por Rol
    @Operation(summary = "Obtiene usuarios por rol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios encontrados", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "204", description = "No se encontraron usuarios con ese rol", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<List<Usuario>> getUsuariosByRol(@RequestParam(value = "rol") String rol) {
        List<Usuario> usuarios;
        try {
            usuarios = usuarioService.findUsuariosByRol(rol);
        } catch (RolNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Crear un nuevo usuario
    @Operation(summary = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario, @RequestParam(value = "rol") String rol) {
        try {
            Usuario usuarioConRol = usuarioService.crearUsuarioConRol(usuario, rol);
            return new ResponseEntity<>(usuarioConRol, HttpStatus.CREATED);
        } catch (RolNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // Actualizar un usuario existente
    @Operation(summary = "Actualiza un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {
            Usuario updatedUsuario = usuario.get();
            updatedUsuario.setNombre(usuarioDetails.getNombre());
            updatedUsuario.setEmail(usuarioDetails.getEmail());
            updatedUsuario.setPassword(usuarioDetails.getPassword());
            updatedUsuario.setActivo(usuarioDetails.getActivo());
            updatedUsuario.setFechaCreacion(usuarioDetails.getFechaCreacion());
            usuarioService.save(updatedUsuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar el rol de un usuario
    @Operation(summary = "Actualiza el rol de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario con rol actualizado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario o rol no encontrado", content = @Content)
    })
    @PatchMapping("/updateRole")
    @Transactional
    public ResponseEntity<Usuario> updateUsuarioRole(@RequestParam(value = "id_usuario") Long usuarioID, @RequestParam(value = "rol") String rol) {
        try {
            Usuario usuarioActualizado = usuarioService.asignarRolAUsuario(usuarioID, rol);
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } catch (UsuarioNotFoundException | RolNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Borrado lógico de un usuario.
     * @param id ID del usuario a desactivar.
     * @return Respuesta de desactivación del usuario.
     */
    @Operation(summary = "Desactiva un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario desactivado", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Usuario> deleteUsuarioLogico(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {
            Usuario usuarioExistente = usuario.get();
            usuarioExistente.setActivo(false);
            usuarioService.save(usuarioExistente);
            return ResponseEntity.ok(usuarioExistente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Eliminar un usuario permanente
    @Operation(summary = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            if (usuarioService.existsById(id)) {
                usuarioService.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RolNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}

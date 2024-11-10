package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.service.UsuarioService;
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

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @GetMapping("/getAll")
    public List<Usuario> getAllUsuarios() {
        //Cuando no se manipulan los header de la respuesta no es necesario devolver un ResponseEntity
        return usuarioService.findAll();
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario;
        usuario = usuarioService.findById(id);
        if (usuario.isPresent()) return new ResponseEntity<>(usuario, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

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
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        if (usuario.isPresent()) {
            Usuario updatedUsuario = usuario.get();
            updatedUsuario.setNombre(usuarioDetails.getNombre());
            updatedUsuario.setEmail(usuarioDetails.getEmail());
            updatedUsuario.setPassword(usuarioDetails.getPassword());
            updatedUsuario.setEstado(usuarioDetails.getEstado());
            updatedUsuario.setFechaCreacion(usuarioDetails.getFechaCreacion());
            usuarioService.save(updatedUsuario);
            return ResponseEntity.ok(updatedUsuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    // Eliminar un usuario
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

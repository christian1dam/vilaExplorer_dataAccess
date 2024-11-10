package app.VilaExplorer.controller;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.service.FiestaTradicionService;
import app.VilaExplorer.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * Controlador de la API REST de fiestas tradicionales
 * en general esta destinada a ser modificada por los administradores
 * los usuarios comunes solo podran ver las fiestas tradicionales
 */
@RestController
@RequestMapping("/api/fiestas")
public class FiestaTradicionController {

    /**
     * Inyectar el servicio de fiestas tradicionales
     */
    @Autowired
    private FiestaTradicionService fiestaTradicionService;

    /**
     * Inyectar el servicio de usuarios
     */
    @Autowired
    private UsuarioService usuarioService; // Inyectar el UsuarioService para buscar el objeto Usuario

    /**
     * Obtener una fiesta tradicional por su id
     * @param idFiestaTradicion
     * @return  Fiesta tradicional
     */
    @GetMapping("/detalle/{id}")
    public ResponseEntity<FiestaTradicion> getFiestaTradicionById(@PathVariable Long idFiestaTradicion) {
        Optional<FiestaTradicion> fiestaTradicion = fiestaTradicionService.findById(idFiestaTradicion);
        return fiestaTradicion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Obtener todas las fiestas tradicionales
     * @return  Lista de fiestas tradicionales
     */
    @GetMapping("/todos")
    public List<FiestaTradicion> getAllFiestasTradicion() {
        return fiestaTradicionService.findAll();
    }

    /**
     * Crear una fiesta tradicional
     * @param fiestaTradicion
     * @return  Fiesta tradicional creada
     */
    @PostMapping("/crear")
    public FiestaTradicion createFiestaTradicion(@RequestBody FiestaTradicion fiestaTradicion) {
        return fiestaTradicionService.save(fiestaTradicion);
    }

    /**
     * Modificar una fiesta tradicional
     * @param id
     * @param fiestaTradicion
     * @return  Fiesta tradicional modificada
     */
    @PutMapping("/modificar/{id}")
    public ResponseEntity<FiestaTradicion> updateFiestaTradicion(@PathVariable Long id, @RequestBody FiestaTradicion fiestaTradicion) {
        Optional<FiestaTradicion> existingFiestaTradicion = fiestaTradicionService.findById(id);
        if (existingFiestaTradicion.isPresent()) {
            fiestaTradicion.setIdFiestaTradicion(id);
            return ResponseEntity.ok(fiestaTradicionService.save(fiestaTradicion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar una fiesta tradicional
     * @param id
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteFiestaTradicion(@PathVariable Long id) {
        fiestaTradicionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtener todas las fiestas tradicionales de un autor
     * @param idAutor
     * @return  Lista de fiestas tradicionales
     */
    @GetMapping("/autor/{idAutor}")
    public ResponseEntity<List<FiestaTradicion>> getFiestasByAutor(@PathVariable Long idAutor) {
        Optional<Usuario> autor = usuarioService.findById(idAutor);
        if (autor.isPresent()) {
            List<FiestaTradicion> fiestas = fiestaTradicionService.findByAutor(autor.get());
            return ResponseEntity.ok(fiestas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar fiestas tradicionales por palabra clave parcial o completa en el nombre o descripción
     * @param keyword
     * @return  Lista de fiestas tradicionales
     */
    @GetMapping("/buscar_palabra")
    public ResponseEntity<List<FiestaTradicion>> searchFiestas(@RequestParam String keyword) {
        List<FiestaTradicion> results = fiestaTradicionService.searchByKeyword(keyword);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(results);
        }
    }

    /**
     * Buscar fiestas tradicionales por palabra clave parcial o completa en el nombre o descripción paginado
     * paginado significa que se mostrara de a 10 elementos por pagina
     * @param keyword
     * @return  Lista de fiestas tradicionales
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<FiestaTradicion>> searchFiestas(@RequestParam String keyword, Pageable pageable) {
        Page<FiestaTradicion> results = fiestaTradicionService.searchByKeyword(keyword, pageable);
        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(results);
        }
    }


}
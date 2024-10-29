package app.VilaExplorer.controller;

import app.VilaExplorer.domain.CategoriaPlato;
import app.VilaExplorer.service.CategoriaPlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias-plato")
public class CategoriaPlatoController {

    @Autowired
    private CategoriaPlatoService categoriaPlatoService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<CategoriaPlato> getCategoriaPlatoById(@PathVariable Long id) {
        Optional<CategoriaPlato> categoriaPlato = categoriaPlatoService.findById(id);
        return categoriaPlato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<CategoriaPlato> getAllCategoriasPlato() {
        return categoriaPlatoService.findAll();
    }

    @PostMapping("/crear")
    public CategoriaPlato createCategoriaPlato(@RequestBody CategoriaPlato categoriaPlato) {
        return categoriaPlatoService.save(categoriaPlato);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<CategoriaPlato> updateCategoriaPlato(@PathVariable Long id, @RequestBody CategoriaPlato categoriaPlato) {
        Optional<CategoriaPlato> existingCategoriaPlato = categoriaPlatoService.findById(id);
        if (existingCategoriaPlato.isPresent()) {
            categoriaPlato.setId_categoria(id);
            return ResponseEntity.ok(categoriaPlatoService.save(categoriaPlato));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteCategoriaPlato(@PathVariable Long id) {
        categoriaPlatoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

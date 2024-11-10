package app.VilaExplorer.controller;

import app.VilaExplorer.domain.TipoPlato;
import app.VilaExplorer.service.TipoPlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/tipos-plato")
public class TipoPlatoController {

    @Autowired
    private TipoPlatoService tipoPlatoService;

    // Obtener todos los tipos de plato (incluye los desactivados)
    @GetMapping("/all")
    public List<TipoPlato> getAllTiposPlato() {
        return tipoPlatoService.findAll();
    }

    // Obtener todos los tipos de plato activos
    @GetMapping("/activos")
    public List<TipoPlato> getAllTiposPlatoActivos() {
        return tipoPlatoService.findAllActivos();
    }

    // Obtener un tipo de plato por ID
    @GetMapping("/id/{id}")
    public ResponseEntity<TipoPlato> getTipoPlatoById(@PathVariable Long id) {
        Optional<TipoPlato> tipoPlato = tipoPlatoService.findById(id);
        return tipoPlato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear o actualizar un tipo de plato
    @PostMapping("/crear")
    public ResponseEntity<TipoPlato> createOrUpdateTipoPlato(@RequestBody TipoPlato tipoPlato) {
        TipoPlato savedTipoPlato = tipoPlatoService.save(tipoPlato);
        return ResponseEntity.ok(savedTipoPlato);
    }

    // Borrado lógico de un tipo de plato por ID
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteTipoPlatoLogico(@PathVariable Long id) {
        tipoPlatoService.deleteByIdLogico(id);
        return ResponseEntity.ok("Tipo de plato eliminado lógicamente");
    }

    // Obtener todos los tipos de plato activos por ID de categoría
    @GetMapping("/categoria/{categoriaId}")
    public List<TipoPlato> getTiposPlatoByCategoriaId(@PathVariable Long categoriaId) {
        return tipoPlatoService.findByCategoriaId(categoriaId);
    }
}

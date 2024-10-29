package app.VilaExplorer.controller;

import app.VilaExplorer.domain.LugarInteres;
import app.VilaExplorer.service.LugarInteresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lugares")
public class LugarInteresController {

    @Autowired
    private LugarInteresService lugarInteresService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<LugarInteres> getLugarInteresById(@PathVariable Long id) {
        Optional<LugarInteres> lugarInteres = lugarInteresService.findById(id);
        return lugarInteres.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<LugarInteres> getAllLugaresInteres() {
        return lugarInteresService.findAll();
    }

    @PostMapping("/crear")
    public LugarInteres createLugarInteres(@RequestBody LugarInteres lugarInteres) {
        return lugarInteresService.save(lugarInteres);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteLugarInteres(@PathVariable Long id) {
        lugarInteresService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
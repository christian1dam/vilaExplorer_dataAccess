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

    // GET all lugares
    @GetMapping
    public List<LugarInteres> getAllLugares() {
        return lugarInteresService.findAll();
    }

    // GET lugar by ID
    @GetMapping("/{id}")
    public ResponseEntity<LugarInteres> getLugarById(@PathVariable Long id) {
        Optional<LugarInteres> lugar = lugarInteresService.findById(id);
        return lugar.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST create new lugar
    @PostMapping
    public LugarInteres createLugar(@RequestBody LugarInteres lugarInteres) {
        return lugarInteresService.save(lugarInteres);
    }

    // PUT update existing lugar
    @PutMapping("/{id}")
    public ResponseEntity<LugarInteres> updateLugar(@PathVariable Long id, @RequestBody LugarInteres lugarDetails) {
        Optional<LugarInteres> lugarOptional = lugarInteresService.findById(id);

        if (!lugarOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        LugarInteres lugar = lugarOptional.get();
        lugar.setNombreLugar(lugarDetails.getNombreLugar());
        lugar.setDescripcion(lugarDetails.getDescripcion());
        lugar.setImagen(lugarDetails.getImagen());
        lugar.setCategoria(lugarDetails.getCategoria());
        // Actualiza otros campos según sea necesario

        LugarInteres updatedLugar = lugarInteresService.save(lugar);
        return ResponseEntity.ok(updatedLugar);
    }

    // DELETE lugar by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLugar(@PathVariable Long id) {
        if (!lugarInteresService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        lugarInteresService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

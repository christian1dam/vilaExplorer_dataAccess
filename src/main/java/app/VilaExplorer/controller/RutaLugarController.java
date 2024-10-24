package app.VilaExplorer.controller;

import app.VilaExplorer.domain.RutaLugar;
import app.VilaExplorer.service.RutaLugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rutas-lugares")
public class RutaLugarController {

    @Autowired
    private RutaLugarService rutaLugarService;

    // GET all rutas-lugares
    @GetMapping
    public List<RutaLugar> getAllRutasLugares() {
        return rutaLugarService.findAll();
    }

    // GET ruta-lugar by ID
    @GetMapping("/{id}")
    public ResponseEntity<RutaLugar> getRutaLugarById(@PathVariable Long id) {
        Optional<RutaLugar> rutaLugar = rutaLugarService.findById(id);
        return rutaLugar.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST create new ruta-lugar
    @PostMapping
    public RutaLugar createRutaLugar(@RequestBody RutaLugar rutaLugar) {
        return rutaLugarService.save(rutaLugar);
    }

    // PUT update existing ruta-lugar
    @PutMapping("/{id}")
    public ResponseEntity<RutaLugar> updateRutaLugar(@PathVariable Long id, @RequestBody RutaLugar rutaLugarDetails) {
        Optional<RutaLugar> rutaLugarOptional = rutaLugarService.findById(id);

        if (!rutaLugarOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        RutaLugar rutaLugar = rutaLugarOptional.get();
        rutaLugar.setId_ruta(rutaLugarDetails.getId_ruta());
        rutaLugar.setId_lugar(rutaLugarDetails.getId_lugar());

        RutaLugar updatedRutaLugar = rutaLugarService.save(rutaLugar);
        return ResponseEntity.ok(updatedRutaLugar);
    }

    // DELETE ruta-lugar by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRutaLugar(@PathVariable Long id) {
        if (!rutaLugarService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        rutaLugarService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

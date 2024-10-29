package app.VilaExplorer.controller;

import app.VilaExplorer.domain.RutaLugar;
import app.VilaExplorer.service.RutaLugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ruta-lugar")
public class RutaLugarController {

    @Autowired
    private RutaLugarService rutaLugarService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<RutaLugar> getRutaLugarById(@PathVariable Long id) {
        Optional<RutaLugar> rutaLugar = rutaLugarService.findById(id);
        return rutaLugar.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<RutaLugar> getAllRutasLugar() {
        return rutaLugarService.findAll();
    }

    @PostMapping("/crear")
    public RutaLugar createRutaLugar(@RequestBody RutaLugar rutaLugar) {
        return rutaLugarService.save(rutaLugar);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<RutaLugar> updateRutaLugar(@PathVariable Long id, @RequestBody RutaLugar rutaLugar) {
        Optional<RutaLugar> existingRutaLugar = rutaLugarService.findById(id);
        if (existingRutaLugar.isPresent()) {
            rutaLugar.setIdRutaLugar(id);
            return ResponseEntity.ok(rutaLugarService.save(rutaLugar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

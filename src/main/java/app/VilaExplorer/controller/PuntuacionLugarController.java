package app.VilaExplorer.controller;

import app.VilaExplorer.domain.PuntuacionLugar;
import app.VilaExplorer.service.PuntuacionLugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/puntuacion-lugar")
public class PuntuacionLugarController {

    @Autowired
    private PuntuacionLugarService puntuacionLugarService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<PuntuacionLugar> getPuntuacionLugarById(@PathVariable Long id) {
        Optional<PuntuacionLugar> puntuacionLugar = puntuacionLugarService.findById(id);
        return puntuacionLugar.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<PuntuacionLugar> getAllPuntuacionesLugar() {
        return puntuacionLugarService.findAll();
    }

    @PostMapping("/crear")
    public PuntuacionLugar createPuntuacionLugar(@RequestBody PuntuacionLugar puntuacionLugar) {
        return puntuacionLugarService.save(puntuacionLugar);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<PuntuacionLugar> updatePuntuacionLugar(@PathVariable Long id, @RequestBody PuntuacionLugar puntuacionLugar) {
        Optional<PuntuacionLugar> existingPuntuacionLugar = puntuacionLugarService.findById(id);
        if (existingPuntuacionLugar.isPresent()) {
            puntuacionLugar.setIdPuntuacion(id);
            return ResponseEntity.ok(puntuacionLugarService.save(puntuacionLugar));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
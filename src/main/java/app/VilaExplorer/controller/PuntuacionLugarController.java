package app.VilaExplorer.controller;

import app.VilaExplorer.domain.PuntuacionLugar;
import app.VilaExplorer.service.PuntuacionLugarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/puntuaciones-lugares")
public class PuntuacionLugarController {

    @Autowired
    private PuntuacionLugarService puntuacionLugarService;

    // GET all puntuaciones
    @GetMapping
    public List<PuntuacionLugar> getAllPuntuaciones() {
        return puntuacionLugarService.findAll();
    }

    // GET puntuacion by ID
    @GetMapping("/{id}")
    public ResponseEntity<PuntuacionLugar> getPuntuacionById(@PathVariable Long id) {
        Optional<PuntuacionLugar> puntuacion = puntuacionLugarService.findById(id);
        return puntuacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST create new puntuacion
    @PostMapping
    public PuntuacionLugar createPuntuacion(@RequestBody PuntuacionLugar puntuacionLugar) {
        return puntuacionLugarService.save(puntuacionLugar);
    }

    // PUT update existing puntuacion
    @PutMapping("/{id}")
    public ResponseEntity<PuntuacionLugar> updatePuntuacion(@PathVariable Long id, @RequestBody PuntuacionLugar puntuacionDetails) {
        Optional<PuntuacionLugar> puntuacionOptional = puntuacionLugarService.findById(id);

        if (!puntuacionOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        PuntuacionLugar puntuacion = puntuacionOptional.get();
        puntuacion.setId_usuario(puntuacionDetails.getId_usuario());
        puntuacion.setId_lugar(puntuacionDetails.getId_lugar());
        puntuacion.setPuntuacion(puntuacionDetails.getPuntuacion());

        PuntuacionLugar updatedPuntuacion = puntuacionLugarService.save(puntuacion);
        return ResponseEntity.ok(updatedPuntuacion);
    }

    // DELETE puntuacion by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePuntuacion(@PathVariable Long id) {
        if (!puntuacionLugarService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        puntuacionLugarService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

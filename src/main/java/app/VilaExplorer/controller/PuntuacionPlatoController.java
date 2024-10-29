package app.VilaExplorer.controller;


import app.VilaExplorer.domain.PuntuacionPlato;
import app.VilaExplorer.service.PuntuacionPlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/puntuacion-plato")
public class PuntuacionPlatoController {

    @Autowired
    private PuntuacionPlatoService puntuacionPlatoService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<PuntuacionPlato> getPuntuacionPlatoById(@PathVariable Long id) {
        Optional<PuntuacionPlato> puntuacionPlato = puntuacionPlatoService.findById(id);
        return puntuacionPlato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<PuntuacionPlato> getAllPuntuacionesPlato() {
        return puntuacionPlatoService.findAll();
    }

    @PostMapping("/crear")
    public PuntuacionPlato createPuntuacionPlato(@RequestBody PuntuacionPlato puntuacionPlato) {
        return puntuacionPlatoService.save(puntuacionPlato);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<PuntuacionPlato> updatePuntuacionPlato(@PathVariable Long id, @RequestBody PuntuacionPlato puntuacionPlato) {
        Optional<PuntuacionPlato> existingPuntuacionPlato = puntuacionPlatoService.findById(id);
        if (existingPuntuacionPlato.isPresent()) {
            puntuacionPlato.setIdPuntuacionPlato(id);
            return ResponseEntity.ok(puntuacionPlatoService.save(puntuacionPlato));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
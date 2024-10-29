package app.VilaExplorer.controller;


import app.VilaExplorer.domain.PuntuacionFiesta;
import app.VilaExplorer.service.PuntuacionFiestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/puntuacion-fiesta")
public class PuntuacionFiestaController {

    @Autowired
    private PuntuacionFiestaService puntuacionFiestaService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<PuntuacionFiesta> getPuntuacionFiestaById(@PathVariable Long id) {
        Optional<PuntuacionFiesta> puntuacionFiesta = puntuacionFiestaService.findById(id);
        return puntuacionFiesta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<PuntuacionFiesta> getAllPuntuacionesFiesta() {
        return puntuacionFiestaService.findAll();
    }

    @PostMapping("/crear")
    public PuntuacionFiesta createPuntuacionFiesta(@RequestBody PuntuacionFiesta puntuacionFiesta) {
        return puntuacionFiestaService.save(puntuacionFiesta);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<PuntuacionFiesta> updatePuntuacionFiesta(@PathVariable Long id, @RequestBody PuntuacionFiesta puntuacionFiesta) {
        Optional<PuntuacionFiesta> existingPuntuacionFiesta = puntuacionFiestaService.findById(id);
        if (existingPuntuacionFiesta.isPresent()) {
            puntuacionFiesta.setIdPuntuacion(id);
            return ResponseEntity.ok(puntuacionFiestaService.save(puntuacionFiesta));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

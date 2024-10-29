package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Coordenada;
import app.VilaExplorer.service.CoordenadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coordenadas")
public class CoordenadaController {

    @Autowired
    private CoordenadaService coordenadaService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Coordenada> getCoordenadaById(@PathVariable Long id) {
        Optional<Coordenada> coordenada = coordenadaService.findById(id);
        return coordenada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<Coordenada> getAllCoordenadas() {
        return coordenadaService.findAll();
    }

    @PostMapping("/crear")
    public Coordenada createCoordenada(@RequestBody Coordenada coordenada) {
        return coordenadaService.save(coordenada);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<Coordenada> updateCoordenada(@PathVariable Long id, @RequestBody Coordenada coordenada) {
        Optional<Coordenada> existingCoordenada = coordenadaService.findById(id);
        if (existingCoordenada.isPresent()) {
            coordenada.setIdCoordenada(id);
            return ResponseEntity.ok(coordenadaService.save(coordenada));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteCoordenada(@PathVariable Long id) {
        coordenadaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
package app.VilaExplorer.controller;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.service.FiestaTradicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fiestas")
public class FiestaTradicionController {

    @Autowired
    private FiestaTradicionService fiestaTradicionService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<FiestaTradicion> getFiestaTradicionById(@PathVariable Long id) {
        Optional<FiestaTradicion> fiestaTradicion = fiestaTradicionService.findById(id);
        return fiestaTradicion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<FiestaTradicion> getAllFiestasTradicion() {
        return fiestaTradicionService.findAll();
    }

    @PostMapping("/crear")
    public FiestaTradicion createFiestaTradicion(@RequestBody FiestaTradicion fiestaTradicion) {
        return fiestaTradicionService.save(fiestaTradicion);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<FiestaTradicion> updateFiestaTradicion(@PathVariable Long id, @RequestBody FiestaTradicion fiestaTradicion) {
        Optional<FiestaTradicion> existingFiestaTradicion = fiestaTradicionService.findById(id);
        if (existingFiestaTradicion.isPresent()) {
            fiestaTradicion.setIdFiestaTradicion(id);
            return ResponseEntity.ok(fiestaTradicionService.save(fiestaTradicion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteFiestaTradicion(@PathVariable Long id) {
        fiestaTradicionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
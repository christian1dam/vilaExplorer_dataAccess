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

    // GET all fiestas
    @GetMapping
    public List<FiestaTradicion> getAllFiestas() {
        return fiestaTradicionService.findAll();
    }

    // GET fiesta by ID
    @GetMapping("/{id}")
    public ResponseEntity<FiestaTradicion> getFiestaById(@PathVariable Integer id) {
        Optional<FiestaTradicion> fiesta = fiestaTradicionService.findById(id);
        return fiesta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST create new fiesta
    @PostMapping
    public FiestaTradicion createFiesta(@RequestBody FiestaTradicion fiestaTradicion) {
        return fiestaTradicionService.save(fiestaTradicion);
    }

    // PUT update existing fiesta
    @PutMapping("/{id}")
    public ResponseEntity<FiestaTradicion> updateFiesta(@PathVariable Integer id, @RequestBody FiestaTradicion fiestaDetails) {
        Optional<FiestaTradicion> fiestaOptional = fiestaTradicionService.findById(id);

        if (!fiestaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        FiestaTradicion fiesta = fiestaOptional.get();
        fiesta.setNombre(fiestaDetails.getNombre());
        fiesta.setDescripcion(fiestaDetails.getDescripcion());
        fiesta.setImagen(fiestaDetails.getImagen());
        fiesta.setAutor(fiestaDetails.getAutor());
        // Actualiza otros campos según sea necesario

        FiestaTradicion updatedFiesta = fiestaTradicionService.save(fiesta);
        return ResponseEntity.ok(updatedFiesta);
    }

    // DELETE fiesta by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFiesta(@PathVariable Integer id) {
        if (!fiestaTradicionService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        fiestaTradicionService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

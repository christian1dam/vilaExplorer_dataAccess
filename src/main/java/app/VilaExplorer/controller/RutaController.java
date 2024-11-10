package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Ruta;
import app.VilaExplorer.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Ruta> getRutaById(@PathVariable Long id) {
        Optional<Ruta> ruta = rutaService.findById(id);
        return ruta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<Ruta> getAllRutas() {
        return rutaService.findAll();
    }

    @PostMapping("/crear")
    public ResponseEntity<Ruta> createRuta(@RequestBody Ruta ruta) {
        // Validar que se hayan enviado coordenadas
        if (ruta.getCoordenadas() == null || ruta.getCoordenadas().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Es obligatorio tener al menos un par de coordenadas
        }

        // Asociar cada coordenada a la ruta
        ruta.getCoordenadas().forEach(coordenada -> coordenada.setRuta(ruta));

        // Guardar la ruta junto con las coordenadas asociadas
        Ruta savedRuta = rutaService.save(ruta);
        return ResponseEntity.ok(savedRuta);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable Long id) {
        rutaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener rutas por autor
    @GetMapping("/autor/{autorId}")
    public List<Ruta> getRutasByAutor(@PathVariable Long autorId) {
        return rutaService.findByAutorId(autorId);
    }
}

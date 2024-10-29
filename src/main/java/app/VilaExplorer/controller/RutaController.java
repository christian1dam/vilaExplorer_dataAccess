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
    public Ruta createRuta(@RequestBody Ruta ruta) {
        return rutaService.save(ruta);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable Long id) {
        rutaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

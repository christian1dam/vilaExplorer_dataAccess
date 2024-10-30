package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.event.ItemListener;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/platos")
public class PlatoController {

    @Autowired
    private PlatoService platoService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Plato> getPlatoById(@PathVariable Long id) {
        Optional<Plato> plato = platoService.findById(id);
        return plato.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public ResponseEntity<Iterable<Plato>> getAllPlatos() {
       Iterable<Plato> platos = platoService.findAll();
        return new ResponseEntity<>(platos, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public Plato createPlato(@RequestBody Plato plato) {
        return platoService.save(plato);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deletePlato(@PathVariable Long id) {
        platoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
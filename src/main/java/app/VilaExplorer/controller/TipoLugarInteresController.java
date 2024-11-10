package app.VilaExplorer.controller;


import app.VilaExplorer.domain.TipoLugarInteres;
import app.VilaExplorer.service.TipoLugarInteresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-lugar")
public class TipoLugarInteresController {

    @Autowired
    private TipoLugarInteresService tipoLugarInteresService;

    //Obtener un tipo de lugar por id
    @GetMapping("/detalle/{id}")
    public ResponseEntity<TipoLugarInteres> getTipoLugarInteresById(@PathVariable Long id) {
        Optional<TipoLugarInteres> tipoLugarInteres = tipoLugarInteresService.findById(id);
        return tipoLugarInteres.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Obtener todos los tipos de lugar
    @GetMapping("/todos")
    public List<TipoLugarInteres> getAllTiposLugarInteres() {
        return tipoLugarInteresService.findAll();
    }

    //Crear un tipo de lugar
    @PostMapping("/crear")
    public TipoLugarInteres createTipoLugarInteres(@RequestBody TipoLugarInteres tipoLugarInteres) {
        return tipoLugarInteresService.save(tipoLugarInteres);
    }

    //Modificar un tipo de lugar
    @PutMapping("/modificar/{id}")
    public ResponseEntity<TipoLugarInteres> updateTipoLugarInteres(@PathVariable Long id, @RequestBody TipoLugarInteres tipoLugarInteres) {
        Optional<TipoLugarInteres> existingTipoLugarInteres = tipoLugarInteresService.findById(id);
        if (existingTipoLugarInteres.isPresent()) {
            tipoLugarInteres.setIdTipoLugar(id);
            return ResponseEntity.ok(tipoLugarInteresService.save(tipoLugarInteres));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar un tipo de lugar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteTipoLugarInteres(@PathVariable Long id) {
        tipoLugarInteresService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

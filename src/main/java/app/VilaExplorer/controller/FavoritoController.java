package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Favorito> getFavoritoById(@PathVariable Long id) {
        Optional<Favorito> favorito = favoritoService.findById(id);
        return favorito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<Favorito> getAllFavoritos() {
        return favoritoService.findAll();
    }

    @PostMapping("/crear")
    public Favorito createFavorito(@RequestBody Favorito favorito) {
        return favoritoService.save(favorito);
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<Favorito> updateFavorito(@PathVariable Long id, @RequestBody Favorito favorito) {
        Optional<Favorito> existingFavorito = favoritoService.findById(id);
        if (existingFavorito.isPresent()) {
            favorito.setIdFavorito(id);
            return ResponseEntity.ok(favoritoService.save(favorito));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long id) {
        favoritoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.enums.TipoEntidad;
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

    /**
     * Devuelve un favorito por su id.
     * @param id el id del favorito
     * @return un ResponseEntity con el favorito o un ResponseEntity con status 404 si no se encuentra
     */
    @GetMapping("/detalle/{id}")
    public ResponseEntity<Favorito> getFavoritoById(@PathVariable Long id) {
        Optional<Favorito> favorito = favoritoService.findById(id);
        return favorito.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Devuelve una lista de todos los favoritos.
     * @return una lista de favoritos
     */
    @GetMapping("/todos")
    public List<Favorito> getAllFavoritos() {
        return favoritoService.findAll();
    }

    /**
     * Devuelve una lista de favoritos de un usuario.
     * @param idUsuario el id del usuario
     * @return una lista de favoritos
     */
    @GetMapping("/usuario/{idUsuario}")
    public List<Favorito> getFavoritosByUsuario(@PathVariable Long idUsuario) {
        return favoritoService.findByUsuarioId(idUsuario);
    }

    /**
     * Devuelve una lista de favoritos de un usuario para una entidad específica.
     * @param idUsuario el id del usuario
     * @param tipoEntidad el tipo de entidad
     * @return una lista de favoritos
     */
    @GetMapping("/usuario/{idUsuario}/tipo/{tipoEntidad}")
    public List<Favorito> getFavoritosByUsuarioAndTipoEntidad(@PathVariable Long idUsuario, @PathVariable TipoEntidad tipoEntidad) {
        return favoritoService.findByUsuarioIdAndTipoEntidad(idUsuario, tipoEntidad);
    }

    /**
     * Guarda un favorito.
     * @param favorito el favorito a guardar
     * @return el favorito guardado
     */
    @PostMapping("/crear")
    public Favorito createFavorito(@RequestBody Favorito favorito) {
        return favoritoService.save(favorito);
    }

    /**
     * Modifica un favorito.
     * @param id el id del favorito
     * @param favorito el favorito modificado
     * @return un ResponseEntity con el favorito modificado o un ResponseEntity con status 404 si no se encuentra
     */
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

    /**
     * Elimina un favorito por su id.
     * @param id el id del favorito
     * @return un ResponseEntity con status 204 en caso de éxito o un ResponseEntity con status 404 si no se encuentra
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteFavorito(@PathVariable Long id) {
        favoritoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package app.VilaExplorer.controller;

import app.VilaExplorer.domain.LugarInteres;
import app.VilaExplorer.service.LugarInteresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lugares-interes")
public class LugarInteresController {

        @Autowired
        private LugarInteresService lugarInteresService;

        @GetMapping
        public List<LugarInteres> listarTodos() {
            return lugarInteresService.obtenerTodos();
        }

        @GetMapping("/{id}")
        public ResponseEntity<LugarInteres> obtenerPorId(@PathVariable Long id) {
            LugarInteres lugarInteres = lugarInteresService.obtenerPorId(id);
            if (lugarInteres != null) {
                return ResponseEntity.ok(lugarInteres);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping
        public LugarInteres crear(@RequestBody LugarInteres lugarInteres) {
            return lugarInteresService.guardar(lugarInteres);
        }

        @PutMapping("/{id}")
        public ResponseEntity<LugarInteres> actualizar(@PathVariable Long id, @RequestBody LugarInteres detallesLugarInteres) {
            LugarInteres lugarExistente = lugarInteresService.obtenerPorId(id);
            if (lugarExistente != null) {
                lugarExistente.setFechaAlta(detallesLugarInteres.getFechaAlta());
                lugarExistente.setNombreLugar(detallesLugarInteres.getNombreLugar());
                lugarExistente.setDescripcion(detallesLugarInteres.getDescripcion());
                lugarExistente.setImagen(detallesLugarInteres.getImagen());
                lugarExistente.setCategoria(detallesLugarInteres.getCategoria());

                LugarInteres actualizado = lugarInteresService.guardar(lugarExistente);
                return ResponseEntity.ok(actualizado);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(@PathVariable Long id) {
            LugarInteres lugarInteres = lugarInteresService.obtenerPorId(id);
            if (lugarInteres != null) {
                lugarInteresService.eliminar(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
}

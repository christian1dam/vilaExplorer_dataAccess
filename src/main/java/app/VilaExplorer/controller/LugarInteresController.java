package app.VilaExplorer.controller;

import app.VilaExplorer.domain.LugarInteres;
import app.VilaExplorer.repository.LugarInteresRepository;
import app.VilaExplorer.repository.PuntuacionLugarRepository;
import app.VilaExplorer.service.LugarInteresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lugares")
public class LugarInteresController {

    @Autowired
    private LugarInteresService lugarInteresService;

    //Soo para Administradores para obtener todos los lugares de interes
    //Metodo para obtener todos los lugares  sin importar si estan activos o no
    //GET /api/lugares/detalle-completo/{id}
    @GetMapping("/detalle-completo/{id}")
    public ResponseEntity<LugarInteres> getLugarInteresById(@PathVariable Long id) {
        Optional<LugarInteres> lugarInteres = lugarInteresService.findById(id);
        return lugarInteres.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Para usuarios comunes
    //Metodo para obtener un lugar de interes activo
    @GetMapping("/detalle/{id}")
    public ResponseEntity<LugarInteres> getLugarInteresActivoById(@PathVariable Long id) {
        Optional<LugarInteres> lugarInteres = lugarInteresService.findById(id);
        return lugarInteres.filter(LugarInteres::getActivo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    //Para Administradores
    //GET /api/lugares/todos incuyendo aquellos que han sido desactivados
    @GetMapping("/todos")
    public List<LugarInteres> getAllLugaresInteres() {
        return lugarInteresService.findAll();
    }

    //Para usuarios comunes
    // GET /api/lugares/todos
    @GetMapping("/activos")
    public List<LugarInteres> getAllLugaresInteresActivos() {
        return lugarInteresService.findAllActivos();
    }


    // POST /api/lugares/crear
    @PostMapping("/crear")
    public LugarInteres createLugarInteres(@RequestBody LugarInteres lugarInteres) {
        lugarInteres.getCoordenadas().forEach(coordenada -> coordenada.setLugarInteres(lugarInteres));//asignar el lugar de interes a las coordenadas
        return lugarInteresService.save(lugarInteres);
    }


    // PUT /api/lugares/modificar/{id}
    @PutMapping("/modificar/{id}")
    public ResponseEntity<LugarInteres> updateLugarInteres(@PathVariable Long id, @RequestBody LugarInteres lugarInteresDetalle) {
        LugarInteres lugarInteres = lugarInteresService.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el lugar de interés con el id: " + id));

        if (!lugarInteres.getActivo()) {
            return ResponseEntity.notFound().build();
        }

        // Actualizando los atributos de LugarInteres
        lugarInteres.setNombreLugar(lugarInteresDetalle.getNombreLugar());
        lugarInteres.setDescripcion(lugarInteresDetalle.getDescripcion());
        lugarInteres.setImagen(lugarInteresDetalle.getImagen());
        lugarInteres.setTipoLugar(lugarInteresDetalle.getTipoLugar());
        lugarInteres.setFechaAlta(lugarInteresDetalle.getFechaAlta());

        // Gestionar las coordenadas: eliminar las existentes y añadir las nuevas
        lugarInteres.getCoordenadas().clear();
        lugarInteres.getCoordenadas().addAll(lugarInteresDetalle.getCoordenadas());
        lugarInteresDetalle.getCoordenadas().forEach(coordenada -> coordenada.setLugarInteres(lugarInteres));

        return ResponseEntity.ok(lugarInteresService.save(lugarInteres));
    }

    //Para Administradores el metodo desactiva un lugar de interes
    // PUT /api/lugares/desactivar/{id}
    @PutMapping("/desactivar/{id}")
    public ResponseEntity<LugarInteres> desactivarLugarInteres(@PathVariable Long id) {
        LugarInteres lugarInteres = lugarInteresService.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el lugar de interés con el id: " + id));

        // Cambiar el estado a inactivo (borrado lógico)
        lugarInteres.setActivo(false);

        return ResponseEntity.ok(lugarInteresService.save(lugarInteres));
    }

    //Para Administradores esta version del metodo elimina un lugar de interes de forma LOGICA (borrado lógico)
    //DELETE /api/lugares/eliminar/{id}
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteLugarInteres(@PathVariable Long id) {
        lugarInteresService.deleteByIdLogico(id);
        return ResponseEntity.ok("Lugar de interés eliminado lógicamente");
    }

}
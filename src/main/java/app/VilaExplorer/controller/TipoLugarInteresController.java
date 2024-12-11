package app.VilaExplorer.controller;


import app.VilaExplorer.domain.TipoLugarInteres;
import app.VilaExplorer.service.TipoLugarInteresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador para la API REST de Tipos de Lugar de Interés.
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@RestController
@RequestMapping("/tipo_lugar_interes")
public class TipoLugarInteresController {

    @Autowired
    private TipoLugarInteresService tipoLugarInteresService;


    //Obtener un tipo de lugar por id
    @Operation(summary = "Obtiene un tipo de lugar por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de lugar encontrado", content = @Content(schema = @Schema(implementation = TipoLugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de lugar no encontrado", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<TipoLugarInteres> getTipoLugarInteresById(@PathVariable Long id) {
        Optional<TipoLugarInteres> tipoLugarInteres = tipoLugarInteresService.findById(id);
        return tipoLugarInteres.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    //Obtener todos los tipos de lugar
    @Operation(summary = "Obtiene todos los tipos de lugar de interés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de tipos de lugar de interés", content = @Content(schema = @Schema(implementation = TipoLugarInteres.class)))
    })
    @GetMapping("/todos")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public List<TipoLugarInteres> getAllTiposLugarInteres() {
        return tipoLugarInteresService.findAll();
    }


    //Crear un tipo de lugar
    @Operation(summary = "Crea un nuevo tipo de lugar de interés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de lugar creado", content = @Content(schema = @Schema(implementation = TipoLugarInteres.class)))
    })
    @PostMapping("/crear")
    @PreAuthorize("hasRole('Administrador')")
    public TipoLugarInteres createTipoLugarInteres(@RequestBody TipoLugarInteres tipoLugarInteres) {
        return tipoLugarInteresService.save(tipoLugarInteres);
    }


    //Modificar un tipo de lugar
    @Operation(summary = "Modifica un tipo de lugar de interés")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de lugar modificado", content = @Content(schema = @Schema(implementation = TipoLugarInteres.class))),
            @ApiResponse(responseCode = "404", description = "Tipo de lugar no encontrado", content = @Content)
    })
    @PutMapping("/modificar/{id}")
    @PreAuthorize("hasRole('Administrador')")
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
    @Operation(summary = "Elimina un tipo de lugar de interés por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de lugar eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tipo de lugar no encontrado", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> deleteTipoLugarInteres(@PathVariable Long id) {
        tipoLugarInteresService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.service.PlatoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

@Controller
@RequestMapping("/plato")
@Tag(name = "Platos", description = "Catálogo de platos")
public class PlatoController {

    private final Logger logger = LoggerFactory.getLogger(Plato.class);


    @Autowired
    private PlatoServiceImpl platoServiceImpl;

    @Operation(summary = "Obtiene una lista de todos los platos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Plato.class)))),
    })

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Plato>> getAllPlatos() {
        List<Plato> platos = platoServiceImpl.findAll();
        return new ResponseEntity<>(platos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un plato en función de su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Existe el plato", content = @Content(schema = @Schema(implementation = Plato.class))),
            @ApiResponse(responseCode = "404", description = "El plato no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Optional<Plato>> getPlatoByID(@PathVariable long id) {
        Optional<Plato> plato = platoServiceImpl.findById(id);
        return new ResponseEntity<>(plato, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el plato", content = @Content(schema = @Schema(implementation = Plato.class)))
    })
    @PostMapping(value = "/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Plato> addPlato(@RequestBody Plato plato) {
        Plato platoNuevo = platoServiceImpl.addPlato(plato);
        return new ResponseEntity<>(platoNuevo, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un plato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el plato", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El plato no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<Response> eliminarPlato(@PathVariable Long id) {
        try {
            platoServiceImpl.eliminarPlato(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PlatoNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Modifica un plato en el catálogo según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica el plato", content = @Content(schema = @Schema(implementation = Plato.class))),
            @ApiResponse(responseCode = "404", description = "El plato no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })

    @PutMapping(value = "/put/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Plato> actualizarPlato(@PathVariable Long id, @RequestBody Plato platoUpdated) {
        Plato plato;
        try {
            plato = platoServiceImpl.actualizarPlato(id, platoUpdated);
            return new ResponseEntity<>(plato, HttpStatus.OK);
        } catch (PlatoNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     @ExceptionHandler(PlatoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(PlatoNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND, pnfe.getMessage());
        logger.error(pnfe.getMessage(), pnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

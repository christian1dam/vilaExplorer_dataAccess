package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Coordenadas;
import app.VilaExplorer.domain.Ruta;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.exception.RutaNotFoundException;
import app.VilaExplorer.repository.UsuarioRepository;
import app.VilaExplorer.service.RutaService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.*;

/**
 * Controlador para la API REST de Rutas.
 *
 * @author VilaExplorerAdmin
 * @version 1.0
 */
@RestController
@RequestMapping("/ruta")
public class RutaController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private RutaService rutaService;


    @Operation(summary = "Obtiene una ruta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta encontrada", content = @Content(schema = @Schema(implementation = Ruta.class))),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada", content = @Content)
    })
    @GetMapping("/detalle/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Ruta> getRutaById(@PathVariable Long id) {
        Optional<Ruta> ruta = rutaService.findById(id);
        return ruta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // =========== GET TODAS LAS RUTAS =============
    @Operation(summary = "Obtiene todas las rutas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de rutas", content = @Content(schema = @Schema(implementation = Ruta.class)))
    })
    @GetMapping("/todos")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public List<Ruta> getAllRutas() {
        return rutaService.findAll();
    }

    // =========== GET RUTAS ACTIVAS =============
    @GetMapping("/activos")
    @Operation(summary = "Obtiene todas las rutas activas")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<List<Ruta>> getAllRutasActivas() {
        List<Ruta> rutasActivas = rutaService.findAllActivas();
        if (rutasActivas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rutasActivas);
    }

    // =========== GET RUTAS INACTIVAS =============
    @Operation(summary = "Obtiene todas las rutas inactivas")
    @GetMapping("/inactivas")
    public ResponseEntity<List<Ruta>> getAllRutasInactivas() {
        List<Ruta> rutasInactivas = rutaService.findAllInactivas();
        if (rutasInactivas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rutasInactivas);
    }


    @Operation(summary = "Crea una nueva ruta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta creada", content = @Content(schema = @Schema(implementation = Ruta.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados invalidos", content = @Content)
    })
    @PostMapping("/crear")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
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


    @Operation(summary = "Crea una ruta pasándo las coordenadas de origen y fin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta creada", content = @Content(schema = @Schema(implementation = Ruta.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados invalidos", content = @Content)
    })
//    SE HACE LA PETICION A OPENROUTESERVICE Y REBOTA AL USUARIO -> NO SE ALMACENA EN NUESTRA BD POR EL MOMENTO
    @GetMapping("/generarRuta")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<?> generarRuta(
            @RequestParam("origenLat") Double origenLat,
            @RequestParam("origenLng") Double origenLng,
            @RequestParam("destinoLat") Double destinoLat,
            @RequestParam("destinoLng") Double destinoLng) {

        try {

            String openRouteUrl = String.format(
                    Locale.US,
                    "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf62485d469bd2cba74cc7bbf095ac9c66e654&start=%f,%f&end=%f,%f",
                    origenLng, origenLat, destinoLng, destinoLat
            );


            ApiClient apiClient = new ApiClient();
            HttpResponse<String> response = apiClient.getRequest(openRouteUrl);
            System.out.println("RESPONSE BODY: " + response.body());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());


            List<Double> bbox = new ArrayList<>();

            if (jsonNode.has("bbox")) {
                for (JsonNode value : jsonNode.get("bbox")) {
                    bbox.add(value.asDouble());
                }
            }

            double distancia = 0;
            double duracion = 0;

            if (jsonNode.get("features").get(0).get("properties").has("segments")) {
                JsonNode segment = jsonNode.get("features").get(0).get("properties").get("segments").get(0);
                distancia = segment.get("distance").asDouble();
                duracion = segment.get("duration").asDouble();
            }

            List<Coordenadas> coordenadasRuta = new ArrayList<>();
            if (jsonNode.get("features").get(0).get("geometry").has("coordinates")) {
                JsonNode coordinatesNode = jsonNode.get("features").get(0).get("geometry").get("coordinates");
                for (JsonNode coord : coordinatesNode) {
                    double longitud = coord.get(0).asDouble();
                    double latitud = coord.get(1).asDouble();
                    Coordenadas nuevaCoordenada = new Coordenadas();
                    nuevaCoordenada.setLatitud(latitud);
                    nuevaCoordenada.setLongitud(longitud);
                    coordenadasRuta.add(nuevaCoordenada);
                }
            }

            Ruta nuevaRuta = new Ruta();
            nuevaRuta.setNombreRuta("Ruta generada automáticamente");
            nuevaRuta.setCoordenadas(coordenadasRuta);
            nuevaRuta.setDistancia(distancia);
            nuevaRuta.setDuracion(duracion);
            nuevaRuta.setBbox(bbox);
            nuevaRuta.setActivo(true);

            return ResponseEntity.ok(jsonNode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error al procesar la ruta");
        }
    }

    @Operation(summary = "Crea una ruta pasándo las coordenadas de origen y fin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta creada", content = @Content(schema = @Schema(implementation = Ruta.class))),
            @ApiResponse(responseCode = "400", description = "Datos proporcionados invalidos", content = @Content)
    })
//    SE HACE LA PETICION A OPENROUTESERVICE Y GUARDA EN LA BD
    @PostMapping("/createRoute")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<?> createRoute(
            @RequestParam Long idAutor,
            @RequestBody Map<String, List<List<Double>>> requestBody,
            @RequestParam String titulo,
            @RequestParam boolean predefinida) {
        try {
            List<List<Double>> coordenadas = requestBody.get("coordinates");
            if (coordenadas == null || coordenadas.isEmpty()) {
                return ResponseEntity.badRequest().body("No se han recibido coordenadas válidas.");
            }

            Optional<Usuario> usuarioOptional = usuarioRepository.findById(idAutor);
            if (usuarioOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("El usuario con ID " + idAutor + " no existe.");
            }
            Usuario autor = usuarioOptional.get();

            String openRouteUrl = "https://api.openrouteservice.org/v2/directions/foot-walking/geojson";

            ObjectMapper objectMapper = new ObjectMapper();
            ApiClient apiClient = new ApiClient();

            ObjectNode jsonRequest = objectMapper.createObjectNode();
            ArrayNode coordinatesNode = objectMapper.createArrayNode();

            for (List<Double> coord : coordenadas) {
                ArrayNode pair = objectMapper.createArrayNode();
                pair.add(coord.get(0));  // Longitud
                pair.add(coord.get(1));  // Latitud
                coordinatesNode.add(pair);
            }

            jsonRequest.set("coordinates", coordinatesNode);

            HttpResponse<String> response = apiClient.postRequest(
                    openRouteUrl,
                    jsonRequest.toString(),
                    "5b3ce3597851110001cf62485d469bd2cba74cc7bbf095ac9c66e654"
            );

            JsonNode jsonNode = objectMapper.readTree(response.body());

            List<Coordenadas> coordenadasRuta = new ArrayList<>();
            if (jsonNode.has("features") && jsonNode.get("features").isArray() && !jsonNode.get("features").isEmpty()) {
                JsonNode firstFeature = jsonNode.get("features").get(0);
                if (firstFeature.has("geometry") && firstFeature.get("geometry").has("coordinates")) {
                    JsonNode coordinatesJsonNode = firstFeature.get("geometry").get("coordinates");
                    for (JsonNode coord : coordinatesJsonNode) {
                        double longitud = coord.get(0).asDouble();
                        double latitud = coord.get(1).asDouble();
                        Coordenadas nuevaCoordenada = new Coordenadas();
                        nuevaCoordenada.setLatitud(latitud);
                        nuevaCoordenada.setLongitud(longitud);
                        nuevaCoordenada.setRuta(null);
                        coordenadasRuta.add(nuevaCoordenada);
                    }
                }
            }

            Ruta nuevaRuta = new Ruta();
            nuevaRuta.setNombreRuta(titulo);
            nuevaRuta.setActivo(true);
            nuevaRuta.setAutor(autor);
            nuevaRuta.setPredefinida(predefinida);

            for (Coordenadas coordenada : coordenadasRuta) {
                coordenada.setRuta(nuevaRuta);
            }

            nuevaRuta.setCoordenadas(coordenadasRuta);

            Ruta rutaDBGuardada = rutaService.save(nuevaRuta);

            return ResponseEntity.ok(rutaDBGuardada);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error al procesar la ruta");
        }
    }


    // Actualizar una ruta
    @PutMapping("/modificar/{id}")
    @Operation(summary = "Modifica una ruta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ruta modificada",
                    content = @Content(schema = @Schema(implementation = Ruta.class))),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada", content = @Content)
    })
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public ResponseEntity<Ruta> updateRuta(@PathVariable Long id, @RequestBody Ruta rutaDetails) {
        try {
            Ruta updatedRuta = rutaService.updateRuta(id, rutaDetails);
            return ResponseEntity.ok(updatedRuta);
        } catch (RutaNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(summary = "Elimina una ruta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ruta eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Ruta no encontrada", content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('Administrador')")
    public ResponseEntity<Void> deleteRuta(@PathVariable Long id) {
        rutaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener rutas por autor
    @Operation(summary = "Obtiene rutas por autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutas encontradas", content = @Content(schema = @Schema(implementation = Ruta.class)))
    })
    @GetMapping("/autor/{autorId}")
    @PreAuthorize("hasRole('Administrador') or hasRole('Cliente')")
    public List<Ruta> getRutasByAutor(@PathVariable Long autorId) {
        return rutaService.findByAutorId(autorId);
    }




    // =========== DESACTIVAR (Borrado lógico) =============
    @PutMapping("/desactivar/{id}")
    @Operation(summary = "Desactiva una ruta (borrado lógico) por su ID")
    public ResponseEntity<Ruta> desactivarRuta(@PathVariable Long id) {
        try {
            Ruta rutaDesactivada = rutaService.desactivarRuta(id);
            return ResponseEntity.ok(rutaDesactivada);
        } catch (RutaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // =========== ACTIVAR ============
    @PutMapping("/activar/{id}")
    @Operation(summary = "Activa una ruta que estaba desactivada")
    public ResponseEntity<Ruta> activarRuta(@PathVariable Long id) {
        try {
            Ruta rutaActivada = rutaService.activarRuta(id);
            return ResponseEntity.ok(rutaActivada);
        } catch (RutaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // =========== ELIMINAR LÓGICAMENTE ============
    @DeleteMapping("/eliminar-logico/{id}")
    @Operation(summary = "Elimina una ruta de forma lógica por su ID")
    public ResponseEntity<Void> deleteRutaLogico(@PathVariable Long id) {
        try {
            rutaService.deleteByIdLogico(id);
            // Podrías devolver un objeto con un mensaje, o directamente 200/204
            return ResponseEntity.ok().build();
        } catch (RutaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    // =========== ACTUALIZAR RUTA NO PREDEFINIDA PARA USUARIOS DE TIPO CLIENTE============

    @PutMapping("/modificar-no-predefinida/{id}")
    @Operation(summary = "Un usuario cliente Modifica una ruta no predefinida por su ID")
    public ResponseEntity<Ruta> updateRutaNoPredefinida(@PathVariable Long id, @RequestBody Ruta rutaDetails) {
        try {
            Ruta updatedRuta = rutaService.updateRutaNoPredefinida(id, rutaDetails);
            return ResponseEntity.ok(updatedRuta);
        } catch (RutaNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * NUEVO ENDPOINT: Permite a un usuario (Cliente) ver sus rutas junto con las predefinidas.
     */
    @GetMapping("/mis-rutas/{autorId}")
    @PreAuthorize("hasRole('Cliente')")
    @Operation(summary = "Obtiene las rutas creadas por el usuario junto con las predefinidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rutas encontradas", content = @Content(schema = @Schema(implementation = Ruta.class)))
    })
    public ResponseEntity<List<Ruta>> getRutasForUser(@PathVariable Long autorId) {
        List<Ruta> rutas = rutaService.findRutasForUser(autorId);
        return ResponseEntity.ok(rutas);
    }

}

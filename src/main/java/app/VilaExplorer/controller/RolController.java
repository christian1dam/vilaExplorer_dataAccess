package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Rol;
import app.VilaExplorer.exception.RolNotFoundException;
import app.VilaExplorer.service.RolService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.VilaExplorer.controller.Response.NOT_FOUND;

@RestController
@RequestMapping(value = "/api/roles")
public class RolController {

    @Autowired
    RolService rolService;

    @GetMapping("/all")
    public List<Rol> getAllRoles() {
        return rolService.getAll();
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<Rol> anyadirRol(@RequestBody Rol rol){
        Rol nuevoRol = rolService.anyadirRol(rol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> eliminarRolPorID(@PathVariable Long id) {
        try{
            rolService.eliminarRolPorID(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RolNotFoundException rnfe){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler(RolNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(RolNotFoundException rcnfe) {
        Response response = Response.errorResonse(NOT_FOUND,
                rcnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

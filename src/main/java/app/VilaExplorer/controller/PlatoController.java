package app.VilaExplorer.controller;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.service.PlatoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/plato")
public class PlatoController {
    @Autowired
    private PlatoServiceImpl platoServiceImpl;

    @GetMapping("/nombre")
    public ResponseEntity<List<Plato>> getAllPlatos(){
        List<Plato> platos= platoServiceImpl.findAll();
        return new ResponseEntity<>(platos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Plato>> getPlatoByID(@PathVariable long id){
        Optional<Plato> plato = platoServiceImpl.findById(id);
        return new ResponseEntity<>(plato, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Plato> addPlato(@RequestBody Plato plato){
        Plato platoNuevo = platoServiceImpl.addPlato(plato);
        return new ResponseEntity<>(platoNuevo, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> eliminarPlato(@PathVariable Long id){
        try{
            platoServiceImpl.eliminarPlato(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PlatoNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

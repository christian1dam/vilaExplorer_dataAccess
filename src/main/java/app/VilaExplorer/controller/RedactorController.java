package app.VilaExplorer.controller;


import app.VilaExplorer.domain.Redactor;
import app.VilaExplorer.service.RedactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/redactores")
public class RedactorController {

    @Autowired
    private RedactorService redactorService;

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Redactor> getRedactorById(@PathVariable Long id) {
        Optional<Redactor> redactor = redactorService.findById(id);
        return redactor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/todos")
    public List<Redactor> getAllRedactores() {
        return redactorService.findAll();
    }

    @PostMapping("/crear")
    public Redactor createRedactor(@RequestBody Redactor redactor) {
        return redactorService.save(redactor);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> deleteRedactor(@PathVariable Long id) {
        redactorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
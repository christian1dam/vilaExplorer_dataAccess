package app.VilaExplorer.service;


import app.VilaExplorer.domain.Redactor;
import java.util.List;
import java.util.Optional;

public interface RedactorService {
    Optional<Redactor> findById(Long id);
    List<Redactor> findAll();
    Redactor save(Redactor redactor);
    void deleteById(Long id);
}
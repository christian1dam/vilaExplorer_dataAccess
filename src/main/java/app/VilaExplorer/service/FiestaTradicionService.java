package app.VilaExplorer.service;

import app.VilaExplorer.domain.FiestaTradicion;

import java.util.List;
import java.util.Optional;

public interface FiestaTradicionService {
    List<FiestaTradicion> findAll();
    Optional<FiestaTradicion> findById(Integer id);
    FiestaTradicion save(FiestaTradicion fiestaTradicion);
    void deleteById(Integer id);
    // Métodos adicionales.....
}
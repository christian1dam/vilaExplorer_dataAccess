package app.VilaExplorer.service;

import app.VilaExplorer.domain.FiestaTradicion;

import java.util.List;
import java.util.Optional;

public interface FiestaTradicionService {
    Optional<FiestaTradicion> findById(Long id);
    List<FiestaTradicion> findAll();
    FiestaTradicion save(FiestaTradicion fiestaTradicion);
    void deleteById(Long id);
}
package app.VilaExplorer.service;

import app.VilaExplorer.domain.Administrador;

import java.util.List;
import java.util.Optional;

public interface AdministradorService {
    Optional<Administrador> findById(Long id);
    List<Administrador> findAll();
    Administrador save(Administrador administrador);
    void deleteById(Long id);
}
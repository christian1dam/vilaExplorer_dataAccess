package app.VilaExplorer.service;

import app.VilaExplorer.domain.LugarInteres;

import java.util.List;
import java.util.Optional;

public interface LugarInteresService {
    Optional<LugarInteres> findById(Long id);
    List<LugarInteres> findAll();
    LugarInteres save(LugarInteres lugarInteres);
    void deleteByIdLogico(Long id); //metodo para desactivar un lugar de interes
    List<LugarInteres> findAllActivos();  // metodo para obtener todos los lugares de interes activos
}


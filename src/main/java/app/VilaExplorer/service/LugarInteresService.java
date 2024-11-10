package app.VilaExplorer.service;

import app.VilaExplorer.domain.LugarInteres;

import java.util.List;
import java.util.Optional;

public interface LugarInteresService {
    Optional<LugarInteres> findById(Long id);
    List<LugarInteres> findAll();
    List<LugarInteres> findAllActivos();
    LugarInteres save(LugarInteres lugarInteres);
    void deleteByIdLogico(Long id);//borrado logico para cambiar el estado de activo a false

}


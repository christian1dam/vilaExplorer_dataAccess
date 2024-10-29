package app.VilaExplorer.service;


import app.VilaExplorer.domain.TipoLugarInteres;

import java.util.List;
import java.util.Optional;

public interface TipoLugarInteresService {
    Optional<TipoLugarInteres> findById(Long id);
    List<TipoLugarInteres> findAll();
    TipoLugarInteres save(TipoLugarInteres tipoLugarInteres);
    void deleteById(Long id);
}

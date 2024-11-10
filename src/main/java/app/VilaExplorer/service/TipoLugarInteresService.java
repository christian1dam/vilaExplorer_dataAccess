package app.VilaExplorer.service;


import app.VilaExplorer.domain.TipoLugarInteres;

import java.util.List;
import java.util.Optional;

public interface TipoLugarInteresService {
    //obtener un tipo de lugar por id
    Optional<TipoLugarInteres> findById(Long id);

    //obtener todos los tipos de lugar
    List<TipoLugarInteres> findAll();

    //guardar un tipo de lugar
    TipoLugarInteres save(TipoLugarInteres tipoLugarInteres);

    //eliminar un tipo de lugar por id
    void deleteById(Long id);
}

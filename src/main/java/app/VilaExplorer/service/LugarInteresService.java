package app.VilaExplorer.service;

import app.VilaExplorer.domain.LugarInteres;
import app.VilaExplorer.exception.CoordenadasNotFoundException;
import app.VilaExplorer.exception.LugarInteresNotActiveException;
import app.VilaExplorer.exception.LugarInteresNotFoundException;

import java.util.List;

public interface LugarInteresService {
    LugarInteres findById(Long id) throws LugarInteresNotFoundException;

    List<LugarInteres> findAll() throws LugarInteresNotFoundException;

    List<LugarInteres> findAllActivos() throws LugarInteresNotFoundException;

    LugarInteres save(LugarInteres lugarInteres) throws CoordenadasNotFoundException;

    void deleteByIdLogico(Long id) throws LugarInteresNotFoundException;//borrado logico para cambiar el estado de activo a false

    LugarInteres findLugarInteresActivoByID(Long id) throws LugarInteresNotFoundException, LugarInteresNotActiveException;

    LugarInteres updateLugarInteres(Long id, LugarInteres lugarInteresDetalle) throws LugarInteresNotFoundException;

    LugarInteres desactivarLugarInteres(Long id) throws LugarInteresNotFoundException;

    void actualizarPuntuacionMediaLugarInteres(Long idLugarInteres);

    LugarInteres activarLugarInteres(Long id) throws LugarInteresNotFoundException;

    List<LugarInteres> searchByKeyword(String keyword);
}


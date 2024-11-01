package app.VilaExplorer.service;

import app.VilaExplorer.domain.LugarInteres;
import app.VilaExplorer.repository.LugarInteresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LugarInteresServiceImpl implements LugarInteresService {
    @Autowired
    private LugarInteresRepository lugarInteresRepository;

    @Override
    public Optional<LugarInteres> findById(Long id) {
        return lugarInteresRepository.findById(id);
    }

    @Override
    public List<LugarInteres> findAll() {
        return lugarInteresRepository.findAll();
    }

    //metodo para obtener todos los lugares de interes activos
    @Override
    public List<LugarInteres> findAllActivos() {
        return lugarInteresRepository.findAll().stream()
                .filter(LugarInteres::getActivo)
                .toList();
    }

    @Override
    public LugarInteres save(LugarInteres lugarInteres) {
        return lugarInteresRepository.save(lugarInteres);
    }

    //metodo para desactivar un lugar de interes
    @Override
    public void deleteByIdLogico(Long id) {
        Optional<LugarInteres> lugarInteres = lugarInteresRepository.findById(id);
        if (lugarInteres.isPresent()) {
            LugarInteres lugar = lugarInteres.get();
            lugar.setActivo(false);
            lugarInteresRepository.save(lugar);
        }
    }

}
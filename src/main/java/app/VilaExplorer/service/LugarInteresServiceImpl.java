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

    @Override
    public LugarInteres save(LugarInteres lugarInteres) {
        return lugarInteresRepository.save(lugarInteres);
    }

    @Override
    public void deleteById(Long id) {
        lugarInteresRepository.deleteById(id);
    }
}
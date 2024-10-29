package app.VilaExplorer.service;


import app.VilaExplorer.domain.TipoLugarInteres;
import app.VilaExplorer.repository.TipoLugarInteresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoLugarInteresServiceImpl implements TipoLugarInteresService {
    @Autowired
    private TipoLugarInteresRepository tipoLugarInteresRepository;

    @Override
    public Optional<TipoLugarInteres> findById(Long id) {
        return tipoLugarInteresRepository.findById(id);
    }

    @Override
    public List<TipoLugarInteres> findAll() {
        return tipoLugarInteresRepository.findAll();
    }

    @Override
    public TipoLugarInteres save(TipoLugarInteres tipoLugarInteres) {
        return tipoLugarInteresRepository.save(tipoLugarInteres);
    }

    @Override
    public void deleteById(Long id) {
        tipoLugarInteresRepository.deleteById(id);
    }
}


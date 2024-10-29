package app.VilaExplorer.service;

import app.VilaExplorer.domain.PuntuacionLugar;
import app.VilaExplorer.repository.PuntuacionLugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuntuacionLugarServiceImpl implements PuntuacionLugarService {
    @Autowired
    private PuntuacionLugarRepository puntuacionLugarRepository;

    @Override
    public Optional<PuntuacionLugar> findById(Long id) {
        return puntuacionLugarRepository.findById(id);
    }

    @Override
    public List<PuntuacionLugar> findAll() {
        return puntuacionLugarRepository.findAll();
    }

    @Override
    public PuntuacionLugar save(PuntuacionLugar puntuacionLugar) {
        return puntuacionLugarRepository.save(puntuacionLugar);
    }

    @Override
    public void deleteById(Long id) {
        puntuacionLugarRepository.deleteById(id);
    }
}

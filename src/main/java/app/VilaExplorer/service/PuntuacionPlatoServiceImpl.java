package app.VilaExplorer.service;

import app.VilaExplorer.domain.PuntuacionPlato;
import app.VilaExplorer.repository.PuntuacionPlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuntuacionPlatoServiceImpl implements PuntuacionPlatoService {
    @Autowired
    private PuntuacionPlatoRepository puntuacionPlatoRepository;

    @Override
    public Optional<PuntuacionPlato> findById(Long id) {
        return puntuacionPlatoRepository.findById(id);
    }

    @Override
    public List<PuntuacionPlato> findAll() {
        return puntuacionPlatoRepository.findAll();
    }

    @Override
    public PuntuacionPlato save(PuntuacionPlato puntuacionPlato) {
        return puntuacionPlatoRepository.save(puntuacionPlato);
    }

    @Override
    public void deleteById(Long id) {
        puntuacionPlatoRepository.deleteById(id);
    }
}
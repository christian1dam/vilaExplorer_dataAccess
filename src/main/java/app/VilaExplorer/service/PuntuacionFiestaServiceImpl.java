package app.VilaExplorer.service;


import app.VilaExplorer.domain.PuntuacionFiesta;
import app.VilaExplorer.repository.PuntuacionFiestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuntuacionFiestaServiceImpl implements PuntuacionFiestaService {
    @Autowired
    private PuntuacionFiestaRepository puntuacionFiestaRepository;

    @Override
    public Optional<PuntuacionFiesta> findById(Long id) {
        return puntuacionFiestaRepository.findById(id);
    }

    @Override
    public List<PuntuacionFiesta> findAll() {
        return puntuacionFiestaRepository.findAll();
    }

    @Override
    public PuntuacionFiesta save(PuntuacionFiesta puntuacionFiesta) {
        return puntuacionFiestaRepository.save(puntuacionFiesta);
    }

    @Override
    public void deleteById(Long id) {
        puntuacionFiestaRepository.deleteById(id);
    }
}

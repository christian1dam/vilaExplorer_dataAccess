package app.VilaExplorer.service;


import app.VilaExplorer.domain.Coordenada;
import app.VilaExplorer.repository.CoordenadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordenadaServiceImpl implements CoordenadaService {
    @Autowired
    private CoordenadaRepository coordenadaRepository;

    @Override
    public Optional<Coordenada> findById(Long id) {
        return coordenadaRepository.findById(id);
    }

    @Override
    public List<Coordenada> findAll() {
        return coordenadaRepository.findAll();
    }

    @Override
    public Coordenada save(Coordenada coordenada) {
        return coordenadaRepository.save(coordenada);
    }

    @Override
    public void deleteById(Long id) {
        coordenadaRepository.deleteById(id);
    }
}
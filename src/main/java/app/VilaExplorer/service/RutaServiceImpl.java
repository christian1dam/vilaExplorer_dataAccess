package app.VilaExplorer.service;


import app.VilaExplorer.domain.Ruta;
import app.VilaExplorer.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutaServiceImpl implements RutaService {
    @Autowired
    private RutaRepository rutaRepository;

    @Override
    public Optional<Ruta> findById(Long id) {
        return rutaRepository.findById(id);
    }

    @Override
    public List<Ruta> findAll() {
        return rutaRepository.findAll();
    }

    @Override
    public Ruta save(Ruta ruta) {
        return rutaRepository.save(ruta);
    }

    @Override
    public void deleteById(Long id) {
        rutaRepository.deleteById(id);
    }
}

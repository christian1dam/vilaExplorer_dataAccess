package app.VilaExplorer.service;

import app.VilaExplorer.domain.RutaLugar;
import app.VilaExplorer.repository.RutaLugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutaLugarServiceImpl implements RutaLugarService {

    @Autowired
    private RutaLugarRepository rutaLugarRepository;

    @Override
    public List<RutaLugar> findAll() {
        return rutaLugarRepository.findAll();
    }

    @Override
    public Optional<RutaLugar> findById(Long id) {
        return rutaLugarRepository.findById(id);
    }

    @Override
    public RutaLugar save(RutaLugar rutaLugar) {
        return rutaLugarRepository.save(rutaLugar);
    }

    @Override
    public void deleteById(Long id) {
        rutaLugarRepository.deleteById(id);
    }

    // Métodos adicionales si los necesitas
}

package app.VilaExplorer.service;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImpl implements PlatoService {
    @Autowired
    private PlatoRepository platoRepository;

    @Override
    public List<Plato> findAll() {
        return platoRepository.findAll();
    }

    @Override
    public Optional<Plato> findById(Long id) {
        return platoRepository.findById(id);
    }

    @Override
    public Plato addPlato(Plato plato) {
        return platoRepository.save(plato);
    }

    public void eliminarPlato(Long id) throws PlatoNotFoundException {
        if (!platoRepository.existsById(id)){
            throw new PlatoNotFoundException("El plato con el ID " + id  +" no se ha encontrado en la base de datos ");
        }
        platoRepository.deleteById(id);
    }

    @Override
    public Plato actualizarPlato(Long id, Plato platoUpdated) throws PlatoNotFoundException {
        Optional<Plato> plato = platoRepository.findById(id);
        if(plato.isEmpty()){
            throw new PlatoNotFoundException("El plato con el ID " + id + " no se ha encontrado en la base de datos.");
        }
        platoUpdated.setPlato_id(plato.get().getPlato_id());
        return platoRepository.save(platoUpdated);
    }
}

package app.VilaExplorer.service;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.exception.PlatoNotFoundException;
import app.VilaExplorer.repository.UsuarioRepository;
import app.VilaExplorer.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatoServiceImpl implements PlatoService {
    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Plato findById(Long id) throws PlatoNotFoundException {
        if (platoRepository.findById(id).isEmpty())
            throw new PlatoNotFoundException("Este ID " + id + " no se encuentra en la base de datos");
        return platoRepository.findById(id).get();
    }

    @Override
    public List<Plato> findAll() throws PlatoNotFoundException {
        if (platoRepository.findAll().isEmpty())
            throw new PlatoNotFoundException("Actualmente la base de datos no cuenta con registros de LugarInteres");
        return platoRepository.findAll();
    }

    @Override
    public Plato save(Plato plato) {
        return platoRepository.save(plato);
    }

    @Override
    public void deleteById(Long id) throws PlatoNotFoundException {
        if (platoRepository.findById(id).isEmpty())
            throw new PlatoNotFoundException("Este ID " + id + " no se encuentra en la base de datos");
        platoRepository.deleteById(id);
    }

    // Implementación  para aprobar un plato
    @Override
    public Plato aprobarPlato(Long platoId, Long aprobadorId) {
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new RuntimeException("No se encontró el plato con el id: " + platoId));

        Usuario aprobador = usuarioRepository.findById(aprobadorId)
                .orElseThrow(() -> new RuntimeException("No se encontró el aprobador con el id: " + aprobadorId));

        plato.setAprobador(aprobador);
        plato.setEstado(true); // Marca el plato como aprobado

        return platoRepository.save(plato);
    }

    @Override
    public Plato createPlato(Plato plato) throws DataIntegrityViolationException {
        if (platoRepository.findByNombre(plato.getNombre()).isPresent()) {
            throw new DataIntegrityViolationException("Este plato ya existe en la base de datos.");
        }
        return platoRepository.save(plato);
    }

    @Override
    public Plato updatePlato(Long id, Plato platoDetalles) throws PlatoNotFoundException {
        if (platoRepository.findById(id).isEmpty())
            throw new PlatoNotFoundException("Este ID " + id + " no se encuentra en la base de datos");

        Plato platoFromDB = platoRepository.findById(id).get();

        platoFromDB.setNombre(platoDetalles.getNombre());
        platoFromDB.setDescripcion(platoDetalles.getDescripcion());
        platoFromDB.setIngredientes(platoDetalles.getIngredientes());
        platoFromDB.setReceta(platoDetalles.getReceta());
        platoFromDB.setTipoPlato(platoDetalles.getTipoPlato());
        platoFromDB.setAutor(platoDetalles.getAutor());

        return platoRepository.save(platoFromDB);
    }
}
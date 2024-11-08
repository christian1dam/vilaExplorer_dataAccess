package app.VilaExplorer.service;

import app.VilaExplorer.domain.Plato;
import app.VilaExplorer.domain.Usuario;
import app.VilaExplorer.repository.UsuarioRepository;
import app.VilaExplorer.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoServiceImpl implements PlatoService {
    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Plato> findById(Long id) {
        return platoRepository.findById(id);
    }

    @Override
    public List<Plato> findAll() {
        return platoRepository.findAll();
    }

    @Override
    public Plato save(Plato plato) {
        return platoRepository.save(plato);
    }

    @Override
    public void deleteById(Long id) {
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
}
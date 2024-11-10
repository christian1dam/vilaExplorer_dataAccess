package app.VilaExplorer.service;

import app.VilaExplorer.domain.TipoPlato;
import app.VilaExplorer.repository.TipoPlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoPlatoServiceimpl implements TipoPlatoService {

    @Autowired
    private TipoPlatoRepository tipoPlatoRepository;

    @Override
    public Optional<TipoPlato> findById(Long id) {
        return tipoPlatoRepository.findById(id);
    }

    @Override
    public List<TipoPlato> findAll() {
        return tipoPlatoRepository.findAll();
    }

    @Override
    public List<TipoPlato> findAllActivos() {
        return tipoPlatoRepository.findByActivoTrue();
    }

    @Override
    public TipoPlato save(TipoPlato tipoPlato) {
        return tipoPlatoRepository.save(tipoPlato);
    }

    @Override
    public void deleteByIdLogico(Long id) {
        Optional<TipoPlato> tipoPlato = tipoPlatoRepository.findById(id);
        if (tipoPlato.isPresent()) {
            TipoPlato tipo = tipoPlato.get();
            tipo.setActivo(false);
            tipoPlatoRepository.save(tipo);
        }
    }

    @Override
    public List<TipoPlato> findByCategoriaId(Long categoriaId) {
        return tipoPlatoRepository.findByCategoriaPlatoIdAndActivoTrue(categoriaId);
    }
}

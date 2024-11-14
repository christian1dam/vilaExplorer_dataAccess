package app.VilaExplorer.service;

import app.VilaExplorer.domain.TipoPlato;
import app.VilaExplorer.exception.TipoPlatoNotFoundException;
import app.VilaExplorer.repository.TipoPlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Optional;

@Service
public class TipoPlatoServiceimpl implements TipoPlatoService {

    @Autowired
    private TipoPlatoRepository tipoPlatoRepository;

    @Override
    public TipoPlato findById(Long id) throws TipoPlatoNotFoundException {
        if (tipoPlatoRepository.findById(id).isEmpty())
            throw new TipoPlatoNotFoundException("El ID " + id + " no existe en la base de datos");
        return tipoPlatoRepository.findById(id).get();
    }

    @Override
    public List<TipoPlato> findAll() throws TipoPlatoNotFoundException {
        if (tipoPlatoRepository.findAll().isEmpty())
            throw new TipoPlatoNotFoundException("No hay TipoPlato en la base de datos");
        return tipoPlatoRepository.findAll();
    }

    @Override
    public List<TipoPlato> findAllActivos() throws TipoPlatoNotFoundException {
        if (tipoPlatoRepository.findByActivoTrue().isEmpty())
            throw new TipoPlatoNotFoundException("Actualmente la base de datos no cuenta con registros de TipoPlato activos");
        return tipoPlatoRepository.findByActivoTrue();
    }

    @Override
    public TipoPlato save(TipoPlato tipoPlato) {
        return tipoPlatoRepository.save(tipoPlato);
    }

    @Override
    public void deleteByIdLogico(Long id) throws TipoPlatoNotFoundException {
        if (tipoPlatoRepository.findById(id).isEmpty())
            throw new TipoPlatoNotFoundException("El ID " + id + " no existe en la base de datos");
        TipoPlato tipoPlato = tipoPlatoRepository.findById(id).get();
        tipoPlato.setActivo(false);
        tipoPlatoRepository.save(tipoPlato);
    }

    @Override
    public List<TipoPlato> findByCategoriaId(Long categoriaId) {
        return tipoPlatoRepository.findByCategoriaPlato_IdCategoriaPlatoAndActivoTrue(categoriaId);
    }

    @Override
    public TipoPlato activarTipoPlato(Long id, String activo) throws TipoPlatoNotFoundException, IllegalArgumentException {
        if (tipoPlatoRepository.findById(id).isEmpty())
            throw new TipoPlatoNotFoundException("El ID " + id + " no existe en la base de datos");
        TipoPlato tipoPlatoFromDB = tipoPlatoRepository.findById(id).get();

        if (!activo.equalsIgnoreCase("True") && !activo.equalsIgnoreCase("False")) {
            throw new IllegalArgumentException("Se ha introducido un `True` o un  `False` mal escrito");
        }

        tipoPlatoFromDB.setActivo(Boolean.parseBoolean(activo));
        return tipoPlatoRepository.save(tipoPlatoFromDB);
    }
}

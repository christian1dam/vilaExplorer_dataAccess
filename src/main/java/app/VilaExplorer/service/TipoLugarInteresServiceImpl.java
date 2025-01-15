package app.VilaExplorer.service;


import app.VilaExplorer.domain.TipoLugarInteres;
import app.VilaExplorer.repository.TipoLugarInteresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoLugarInteresServiceImpl implements TipoLugarInteresService {

    @Autowired
    private TipoLugarInteresRepository tipoLugarInteresRepository;

    //Metodo para obtener un tipo de lugar por id
    @Override
    public Optional<TipoLugarInteres> findById(Long id) {
        return tipoLugarInteresRepository.findById(id);
    }

    //Metodo para obtener todos los tipos de lugar
    @Override
    public List<TipoLugarInteres> findAll() {
        return tipoLugarInteresRepository.findAll();
    }

    //Metodo para guardar un tipo de lugar
    @Override
    public TipoLugarInteres save(TipoLugarInteres tipoLugarInteres) {
        return tipoLugarInteresRepository.save(tipoLugarInteres);
    }

    // Borrado lógico: marca 'activo' como false en lugar de eliminar físicamente
    @Override
    public void deleteById(Long id) {
        Optional<TipoLugarInteres> optionalTipo = tipoLugarInteresRepository.findById(id);
        if (optionalTipo.isPresent()) {
            TipoLugarInteres tipoLugar = optionalTipo.get();
            tipoLugar.setActivo(false);
            tipoLugarInteresRepository.save(tipoLugar);
        }
        // Si no existe, no hacemos nada o podríamos lanzar una excepción
    }
}


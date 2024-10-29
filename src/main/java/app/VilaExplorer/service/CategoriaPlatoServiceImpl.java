package app.VilaExplorer.service;


import app.VilaExplorer.domain.CategoriaPlato;
import app.VilaExplorer.repository.CategoriaPlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaPlatoServiceImpl implements CategoriaPlatoService {
    @Autowired
    private CategoriaPlatoRepository categoriaPlatoRepository;

    @Override
    public Optional<CategoriaPlato> findById(Long id) {
        return categoriaPlatoRepository.findById(id);
    }

    @Override
    public List<CategoriaPlato> findAll() {
        return categoriaPlatoRepository.findAll();
    }

    @Override
    public CategoriaPlato save(CategoriaPlato categoriaPlato) {
        return categoriaPlatoRepository.save(categoriaPlato);
    }

    @Override
    public void deleteById(Long id) {
        categoriaPlatoRepository.deleteById(id);
    }
}

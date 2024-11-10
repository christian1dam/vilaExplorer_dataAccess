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
    public List<CategoriaPlato> findAllActivos() {
        return categoriaPlatoRepository.findByActivoTrue();
    }

    @Override
    public CategoriaPlato save(CategoriaPlato categoriaPlato) {
        return categoriaPlatoRepository.save(categoriaPlato);
    }

    @Override
    public void deleteByIdLogico(Long id) {
        Optional<CategoriaPlato> categoriaPlato = categoriaPlatoRepository.findById(id);
        if (categoriaPlato.isPresent()) {
            CategoriaPlato categoria = categoriaPlato.get();
            categoria.setActivo(false); // Cambiar el estado a inactivo (borrado lógico)
            categoriaPlatoRepository.save(categoria);
        }
    }
}

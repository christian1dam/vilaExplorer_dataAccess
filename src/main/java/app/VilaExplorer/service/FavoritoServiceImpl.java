package app.VilaExplorer.service;


import app.VilaExplorer.domain.Favorito;
import app.VilaExplorer.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritoServiceImpl implements FavoritoService {
    @Autowired
    private FavoritoRepository favoritoRepository;

    @Override
    public Optional<Favorito> findById(Long id) {
        return favoritoRepository.findById(id);
    }

    @Override
    public List<Favorito> findAll() {
        return favoritoRepository.findAll();
    }

    @Override
    public Favorito save(Favorito favorito) {
        return favoritoRepository.save(favorito);
    }

    @Override
    public void deleteById(Long id) {
        favoritoRepository.deleteById(id);
    }
}
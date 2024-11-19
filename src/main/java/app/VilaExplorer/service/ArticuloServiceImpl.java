package app.VilaExplorer.service;


import app.VilaExplorer.domain.Articulo;
import app.VilaExplorer.exception.ArticuloNotFoundException;
import app.VilaExplorer.exception.UsuarioNotFoundException;
import app.VilaExplorer.repository.ArticuloRepository;
import app.VilaExplorer.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticuloServiceImpl implements ArticuloService {
    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public Articulo findById(Long id) throws ArticuloNotFoundException {
        if (articuloRepository.findById(id).isEmpty())
            throw new ArticuloNotFoundException("El artículo no existe en la base de datos");
        return articuloRepository.findById(id).get();
    }

    @Override
    public List<Articulo> findAll() throws ArticuloNotFoundException {
        if (articuloRepository.findAll().isEmpty())
            throw new ArticuloNotFoundException("No existen articulos en la base de datos");
        return articuloRepository.findAll();
    }

    @Override
    @Transactional
    public Articulo save(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    @Override
    public void deleteById(Long id) throws ArticuloNotFoundException {
        if(articuloRepository.findById(id).isEmpty())
            throw new ArticuloNotFoundException("El articulo no existe en la base de datos");
        articuloRepository.deleteById(id);
    }

    @Override
    public List<Articulo> findByAutor(Long idAutor) throws UsuarioNotFoundException {
        if (usuarioRepository.findById(idAutor).isEmpty())
            throw new UsuarioNotFoundException("Este usuario no existe en la base de datos");
        return articuloRepository.findByAutor(usuarioRepository.findById(idAutor).get());
    }
}

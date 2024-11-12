package app.VilaExplorer.service;


import app.VilaExplorer.domain.CategoriaPlato;
import app.VilaExplorer.exception.CategoriaPlatoNotFoundException;
import app.VilaExplorer.repository.CategoriaPlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaPlatoServiceImpl implements CategoriaPlatoService {
    @Autowired
    private CategoriaPlatoRepository categoriaPlatoRepository;

    @Override
    public CategoriaPlato findById(Long id) throws CategoriaPlatoNotFoundException {
        if (categoriaPlatoRepository.findById(id).isEmpty())
            throw new CategoriaPlatoNotFoundException("El ID " + id + " no pertenece a ninguna categoría");
        return categoriaPlatoRepository.findById(id).get();
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
    public CategoriaPlato crearCategoriaPlato(CategoriaPlato categoriaPlato) throws DataIntegrityViolationException {
        if(categoriaPlatoRepository.findById(categoriaPlato.getIdCategoriaPlato()).isPresent()){
            throw new DataIntegrityViolationException("Este rol ya existe en la base de datos");
        }
        return categoriaPlatoRepository.save(categoriaPlato);
    }

    @Override
    public void deleteByIdLogico(Long id) throws CategoriaPlatoNotFoundException {
        if (categoriaPlatoRepository.findById(id).isEmpty())
            throw new CategoriaPlatoNotFoundException("El ID " + id + " no pertenece a ninguna categoría.");
        CategoriaPlato categoria = categoriaPlatoRepository.findById(id).get();
        categoria.setActivo(false); // Cambiar el estado a inactivo (borrado lógico)
        categoriaPlatoRepository.save(categoria);
    }

    @Override
    public CategoriaPlato updateCategoriaPlato(Long id, CategoriaPlato categoriaPlato) throws CategoriaPlatoNotFoundException {
        if (categoriaPlatoRepository.findById(id).isEmpty())
            throw new CategoriaPlatoNotFoundException("El ID " + id + " no pertenece a ninguna categoría");
        CategoriaPlato categoriaPlatoFromDB = categoriaPlatoRepository.findById(id).get();

        categoriaPlatoFromDB.setIdCategoriaPlato(categoriaPlato.getIdCategoriaPlato());
        categoriaPlatoFromDB.setNombreCategoria(categoriaPlato.getNombreCategoria());
        categoriaPlatoFromDB.setActivo(categoriaPlato.getActivo());

        return categoriaPlatoRepository.save(categoriaPlatoFromDB);
    }
}

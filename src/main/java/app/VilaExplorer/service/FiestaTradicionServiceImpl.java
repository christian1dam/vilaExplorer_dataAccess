package app.VilaExplorer.service;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.repository.FiestaTradicionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FiestaTradicionServiceImpl implements FiestaTradicionService {

    @Autowired
    private FiestaTradicionRepository fiestaTradicionRepository;

    @Override
    public List<FiestaTradicion> findAll() {
        return fiestaTradicionRepository.findAll();
    }

    @Override
    public Optional<FiestaTradicion> findById(Integer id) {
        return fiestaTradicionRepository.findById(id);
    }

    @Override
    public FiestaTradicion save(FiestaTradicion fiestaTradicion) {
        return fiestaTradicionRepository.save(fiestaTradicion);
    }

    @Override
    public void deleteById(Integer id) {
        fiestaTradicionRepository.deleteById(id);
    }

    // metodos adicionales ...
}
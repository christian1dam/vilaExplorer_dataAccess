package app.VilaExplorer.service;


import app.VilaExplorer.domain.Redactor;
import app.VilaExplorer.repository.RedactorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedactorServiceImpl implements RedactorService {
    @Autowired
    private RedactorRepository redactorRepository;

    @Override
    public Optional<Redactor> findById(Long id) {
        return redactorRepository.findById(id);
    }

    @Override
    public List<Redactor> findAll() {
        return redactorRepository.findAll();
    }

    @Override
    public Redactor save(Redactor redactor) {
        return redactorRepository.save(redactor);
    }

    @Override
    public void deleteById(Long id) {
        redactorRepository.deleteById(id);
    }
}
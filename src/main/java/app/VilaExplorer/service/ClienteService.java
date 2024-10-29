package app.VilaExplorer.service;


import app.VilaExplorer.domain.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    Cliente save(Cliente cliente);
    void deleteById(Long id);
}
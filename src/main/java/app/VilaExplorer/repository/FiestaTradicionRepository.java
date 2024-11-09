package app.VilaExplorer.repository;

import app.VilaExplorer.domain.FiestaTradicion;
import app.VilaExplorer.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiestaTradicionRepository extends JpaRepository<FiestaTradicion, Long> {
    List<FiestaTradicion> findByAutor(Usuario autor);
}

package app.VilaExplorer.repository;


import app.VilaExplorer.domain.Articulo;
import app.VilaExplorer.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    List<Articulo> findByAutorId(Usuario idAutor);
}

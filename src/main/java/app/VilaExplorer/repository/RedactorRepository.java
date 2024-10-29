package app.VilaExplorer.repository;

import app.VilaExplorer.domain.Redactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedactorRepository extends JpaRepository<Redactor, Long> {
}

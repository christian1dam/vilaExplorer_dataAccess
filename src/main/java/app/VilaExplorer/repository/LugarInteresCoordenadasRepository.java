package app.VilaExplorer.repository;


import app.VilaExplorer.domain.LugarInteresCoordenadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LugarInteresCoordenadasRepository extends JpaRepository<LugarInteresCoordenadas, Long> {
}

package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpeciesRepository extends JpaRepository<Species, Integer> {
    Species save(Species species);

    Species findByName(String name);
}

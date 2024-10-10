package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ISpeciesRepository extends JpaRepository<Species, Integer> {
    Species save(Species species);

    @Query("select new SWP391.Fall24.pojo.Species(s.name) " +
            "from Fishes f join f.species s " +
            "where f.id = :fishID")
    List<Species> getListName(@Param("fishID") int fishID);

}

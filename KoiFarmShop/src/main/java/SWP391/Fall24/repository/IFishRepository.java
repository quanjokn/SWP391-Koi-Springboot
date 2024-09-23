package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Fishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFishRepository extends JpaRepository<Fishes, Integer> {
}

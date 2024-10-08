package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Evaluations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEvaluationRepository extends JpaRepository<Evaluations, Integer> {
    List<Evaluations> getEvaluationsByFishId(int fishId);
}

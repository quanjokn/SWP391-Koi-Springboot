package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Batches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBatchRepository extends JpaRepository<Batches, Integer> {
}

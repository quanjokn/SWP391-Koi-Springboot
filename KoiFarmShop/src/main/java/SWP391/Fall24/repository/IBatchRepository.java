package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Batches;
import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBatchRepository extends JpaRepository<Batches, Integer> {
    Optional<Batches> findByFishId(int fishId);
}

package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Origins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOriginRepository extends JpaRepository<Origins, Integer> {
    Origins findById(int origin);
}

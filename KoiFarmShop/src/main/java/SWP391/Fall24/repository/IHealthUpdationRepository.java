package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.CaredKois;
import SWP391.Fall24.pojo.HealthUpdation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHealthUpdationRepository extends JpaRepository<HealthUpdation, Integer> {
    public List<HealthUpdation> findAllByCaredKoi(CaredKois caredKoi);
}

package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.CaringOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICaringOrderRepository extends JpaRepository<CaringOrders, Integer> {
}

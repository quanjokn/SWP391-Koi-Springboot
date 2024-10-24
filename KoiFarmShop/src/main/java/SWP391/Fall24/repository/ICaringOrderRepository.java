package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.CaringOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICaringOrderRepository extends JpaRepository<CaringOrders, Integer> {
    CaringOrders findById(int orderID);
    List<CaringOrders> findByCustomerId(int userId);

    List<CaringOrders> findByStaffId(int staffID);
}

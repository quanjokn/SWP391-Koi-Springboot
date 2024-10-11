package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.OrderDetails;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, Integer> {
    Optional<Orders> findById(int orderId);

    //for staff

    List<Orders> findAll();

    List<Orders> findByStaffId(int staffId);

    List<Orders> findByCustomerId(int userId);
}

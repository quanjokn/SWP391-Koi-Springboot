package SWP391.Fall24.repository;

import SWP391.Fall24.dto.OrderDetailsDTO;
import SWP391.Fall24.pojo.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetails , Integer> {

    List<OrderDetails> findByOrdersId(int orderId);

}

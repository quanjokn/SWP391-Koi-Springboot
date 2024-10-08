package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.pojo.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IFeedbackRepository extends JpaRepository<OrderDetails,Integer> {
    OrderDetails findByOrdersIdAndFishesId(int fishId , int orderId );
    List<OrderDetails> findByFishesId(int fishId);
}

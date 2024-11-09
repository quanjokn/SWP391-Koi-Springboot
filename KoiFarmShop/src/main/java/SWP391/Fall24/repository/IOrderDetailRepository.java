package SWP391.Fall24.repository;

import SWP391.Fall24.dto.OrderDetailsDTO;
import SWP391.Fall24.pojo.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetails , Integer> {
    List<OrderDetails> findByApprovalStatus(String approvalStatus);
    List<OrderDetails> findByOrdersId(int orderId);
    List<OrderDetails> findByFishesId(int fishId);

    //chỉ lấy cá của shop (category = Koi)
    @Query(value = "SELECT TOP 4 od.fishId, SUM(od.quantity) AS totalQuantity " +
            "FROM order_details od " +
            "JOIN Orders o ON o.id=od.orderId " +
            "WHERE o.status ='Completed' " +
            "GROUP BY od.fishId " +
            "ORDER BY totalQuantity DESC"
            , nativeQuery = true)
    List<Object[]> findTop4FishByQuantity();

    OrderDetails findByOrdersIdAndFishesId(int orderId, int fishId);
    List<OrderDetails> findByFishesIdAndApprovalStatus(int fishId, String approvalStatus);

}

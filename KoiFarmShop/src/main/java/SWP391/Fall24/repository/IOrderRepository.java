package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Orders;
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

    //for dashboard


    @Query(value = "SELECT ((DAY(o.date) - 1) / 7) + 1 AS weekOfMonth, " +
            "COUNT(*) AS totalOrders, " +
            "SUM(o.total) AS totalRevenue " +
            "FROM Orders o " +
            "WHERE YEAR(o.date) = :year AND MONTH(o.date) = :month " +
            "AND o.status = 'Completed' " +
            "GROUP BY ((DAY(o.date) - 1) / 7) + 1",
            nativeQuery = true)
    List<Object[]> findOrdersAndRevenueByWeek(@Param("year") int year, @Param("month") int month);


    @Query(value = "SELECT od.fishId AS fishId, SUM(od.quantity) AS totalQuantity " +
            "FROM Orders o " +
            "JOIN order_details od ON o.Id = od.orderid " +
            "WHERE YEAR(o.date) = :year AND MONTH(o.date) = :month " +
            "AND o.status = 'Completed' " +
            "GROUP BY od.fishId " +
            "ORDER BY totalQuantity DESC",
            nativeQuery = true)
    List<Object[]> findSalesByMonth(@Param("year") int year, @Param("month") int month);

    @Query(value = "SELECT SUM(o.total) AS totalRevenue " +
            "FROM Orders o " +
            "WHERE o.status = 'Completed'" +
            "AND YEAR(o.date) = :year AND MONTH(o.date) = :month " ,
            nativeQuery = true)
    double findAllOrderRevenue( int year , int month);
}

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

    @Query(value = "SELECT SUM(total_price) FROM caring_orders cao " +
            "WHERE YEAR(cao.date) = :year AND MONTH(cao.date) = :month " +
            "AND cao.status IN ('Paid', 'Done') " +
            "AND ((DAY(cao.date) - 1) / 7 + 1) = :week", nativeQuery = true)
    Double findTotalForCaringOrder(int year, int month, int week);

    @Query(value = "SELECT SUM(cao.total_price) AS totalRevenue " +
            "FROM caring_orders cao " +
            "WHERE cao.status IN ('Paid', 'Done') " +
            "AND YEAR(cao.date) = :year AND MONTH(cao.date) = :month", nativeQuery = true)
    Double findAllCaringOrder(int year, int month);
}

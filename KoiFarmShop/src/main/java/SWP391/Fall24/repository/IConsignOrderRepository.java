package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IConsignOrderRepository  extends JpaRepository<ConsignOrders, Integer> {

    List<ConsignOrders> findAllByUser(Users user);

    Optional<ConsignOrders> findById(int id);

    List<ConsignOrders> findByStaffId(int staffID);

    @Query(value = "SELECT SUM(total_price) FROM consign_orders co " +
            "WHERE YEAR(co.date) = :year AND MONTH(co.date) = :month " +
            "AND ((DAY(co.date) - 1) / 7 + 1) = :week " +
            "AND co.status = 'Shared'", nativeQuery = true)
    Double findTotalForConsignOrders(int year , int month , int week);

    @Query(value = "  SELECT SUM(co.total_price) AS totalRevenue " +
            "FROM consign_orders co WHERE co.status = 'Shared' " +
            "AND YEAR(co.date) = :year AND MONTH(co.date) = :month", nativeQuery = true)
    Double findAllConsignOrders(int year , int month);
}

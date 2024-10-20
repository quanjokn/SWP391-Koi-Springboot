package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IConsignOrderRepository  extends JpaRepository<ConsignOrders, Integer> {

    List<ConsignOrders> findAllByUser(Users user);

    Optional<ConsignOrders> findById(int id);

    List<ConsignOrders> findByStaffId(int staffID);
}

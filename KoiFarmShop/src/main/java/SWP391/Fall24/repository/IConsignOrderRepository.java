package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConsignOrderRepository  extends JpaRepository<ConsignOrders, Integer> {

    List<ConsignOrders> findAllByUser(Users user);

    ConsignOrders findById(int id);

}

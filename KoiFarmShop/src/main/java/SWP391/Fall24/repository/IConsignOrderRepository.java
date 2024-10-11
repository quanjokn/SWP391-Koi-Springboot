package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.ConsignedKois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConsignOrderRepository  extends JpaRepository<ConsignOrders, Integer> {
}

package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.ConsignedKois;
import SWP391.Fall24.pojo.Fishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IConsignedKoiRepository extends JpaRepository<ConsignedKois, Fishes> {
    List<ConsignedKois> findByConsignOrder(ConsignOrders consignOrder);

    Optional<ConsignedKois> findConsignedKoisById(Integer consignOrderId);

}

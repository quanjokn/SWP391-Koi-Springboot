package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.OrderDateStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDateStatus extends JpaRepository<OrderDateStatus, Integer> {
}

package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Promotions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPromotionRepository extends JpaRepository<Promotions, Integer> {
}

package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Integer> {
}

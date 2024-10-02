package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUserId(int userId);
}
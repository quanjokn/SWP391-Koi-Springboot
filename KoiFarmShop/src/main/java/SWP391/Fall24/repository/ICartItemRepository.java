package SWP391.Fall24.repository;

import SWP391.Fall24.dto.CartItemDTO;
import SWP391.Fall24.pojo.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query("select new SWP391.Fall24.pojo.CartItem(ci.fish.id, ci.cart, ci.fish, ci.quantity, ci.unitPrice, ci.totalPrice) from CartItem ci where ci.cart.id = :cartId ")
    List<CartItem> findByCardId(@Param("cartId") int cardId);
}

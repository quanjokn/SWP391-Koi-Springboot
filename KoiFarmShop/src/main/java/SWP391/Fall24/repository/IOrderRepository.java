package SWP391.Fall24.repository;

import SWP391.Fall24.dto.Cart;
import SWP391.Fall24.dto.Item;
import SWP391.Fall24.pojo.OrderStatus;
import SWP391.Fall24.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IOrderRepository extends JpaRepository<Orders, Integer> {

//    @Query("insert into Orders(total, date, status) values (?1, ?2, ?3)")
//    public void saveOrder(Cart cart){
//        Orders o = new Orders();
//        float total = 0;
//        for(Item i: cart.getCart().values()){
//            total += i.getPrice()*i.getQuantity();
//        }
//        LocalDate date = LocalDate.now();
//        o.setTotal(total);
//        o.setDate(date);
//        o.setStatus(OrderStatus.valueOf("Pending_confirmation"));
//        iOrderService.saveOrder(o);
//    }

}

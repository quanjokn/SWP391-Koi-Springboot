package SWP391.Fall24.service;

import SWP391.Fall24.dto.CartDTO;
import SWP391.Fall24.dto.CartItemDTO;
import SWP391.Fall24.pojo.OrderStatus;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository iOrderRepository;

    @Override
    public Orders saveOrder(CartDTO cartDTO, Users user) {
        Orders o = new Orders();
        float total = 0;
        for(CartItemDTO i: cartDTO.getCartItems()){
            total += i.getUnitPrice() * i.getQuantity();
        }
        LocalDate date = LocalDate.now();

        o.setTotal(total);
        o.setDate(date);
        o.setStatus(OrderStatus.valueOf("Pending_confirmation"));
        o.setCustomer(user);
 
        return iOrderRepository.save(o);
    }
}

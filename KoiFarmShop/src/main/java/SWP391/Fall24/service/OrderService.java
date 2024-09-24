package SWP391.Fall24.service;

import SWP391.Fall24.dto.Cart;
import SWP391.Fall24.dto.Item;
import SWP391.Fall24.pojo.OrderStatus;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository iOrderRepository;


    @Override
    public void saveOrder(Cart cart) {
        Orders o = new Orders();
        float total = 0;
        for(Item i: cart.getCart().values()){
            total += i.getPrice()*i.getQuantity();
        }
        LocalDate date = LocalDate.now();
        o.setTotal(total);
        o.setDate(date);
        o.setStatus(OrderStatus.valueOf("Pending_confirmation"));
        iOrderRepository.save(o);
    }
}

package SWP391.Fall24.service;

import SWP391.Fall24.dto.Cart;
import SWP391.Fall24.pojo.Orders;

public interface IOrderService {
    public void saveOrder(Cart cart);
}

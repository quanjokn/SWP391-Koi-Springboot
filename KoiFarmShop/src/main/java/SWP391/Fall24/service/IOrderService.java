package SWP391.Fall24.service;

import SWP391.Fall24.dto.CartDTO;
import SWP391.Fall24.dto.OrderDTO;
import SWP391.Fall24.dto.OrderDetailsDTO;
import SWP391.Fall24.pojo.Cart;
import SWP391.Fall24.pojo.OrderDetails;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.pojo.Users;

import java.util.List;

public interface IOrderService {

    public OrderDTO getOrderDetails(int cartId);
    public void saveOrder(Cart cart, Users user);
}

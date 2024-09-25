package SWP391.Fall24.service;

import SWP391.Fall24.dto.Cart;

import SWP391.Fall24.dto.Item;
import SWP391.Fall24.pojo.OrderDetails;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.pojo.Users;

public interface IOrderService {
    public Orders saveOrder(Cart cart, Users user);
}

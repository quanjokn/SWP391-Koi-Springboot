package SWP391.Fall24.service;

import SWP391.Fall24.dto.CartDTO;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.pojo.Users;

public interface IOrderService {
    public Orders saveOrder(CartDTO cartDTO, Users user);

}

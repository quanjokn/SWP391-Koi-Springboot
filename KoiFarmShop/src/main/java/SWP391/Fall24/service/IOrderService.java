package SWP391.Fall24.service;

import SWP391.Fall24.dto.CartDTO;
import SWP391.Fall24.dto.OrderDTO;
import SWP391.Fall24.dto.OrderDetailsDTO;
import SWP391.Fall24.dto.PlaceOrderDTO;
import SWP391.Fall24.pojo.Cart;
import SWP391.Fall24.pojo.OrderDetails;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.pojo.Users;

import java.util.List;

public interface IOrderService {

    public OrderDTO getOrderDetails(int cartId);
    public int saveOrder(Cart cart, Users user , PlaceOrderDTO placeOrderDTO);

    //for staff
    public List<Orders> getAllOrders();
    public List<Orders> getStaffOrders(int staffId);
    public Orders receiveOrder(int orderId , int staffId);
    public Orders prepareOrder(int orderId );
    public Orders rejectOrder(int orderId );
    public List<OrderDetails> getUserOrderDetails(int orderId);
    public Orders acceptedOrder(int orderId);
}

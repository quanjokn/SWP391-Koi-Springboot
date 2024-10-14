package SWP391.Fall24.service;

import SWP391.Fall24.dto.*;
import SWP391.Fall24.dto.Staff.AllOrderDTO;
import SWP391.Fall24.pojo.Cart;
import SWP391.Fall24.pojo.Enum.OrderStatus;
import SWP391.Fall24.pojo.OrderDetails;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.pojo.Users;

import java.util.List;

public interface IOrderService {

    public OrderDTO getOrderDetails(int orderId);
    public int saveOrder(Cart cart, Users user , PlaceOrderDTO placeOrderDTO);
    public List<Orders> findOrderByUserId(int userId);
    public List<OrderDetails> getOrderDetailsForStaff(int orderId);
    public List<Orders> getAllOrders();
    public List<Orders> getStaffOrders(int staffId);
    public Orders receiveOrder(int orderId , int staffId);
    public Orders handleOrder(int orderId , OrderStatus status);
    public Orders rejectOrder(OrderManagementDTO orderManagementDTO);
    public AllOrderDTO getAllOrdersForStaff(int userId);

}

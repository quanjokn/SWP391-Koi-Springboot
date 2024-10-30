package SWP391.Fall24.service;

import SWP391.Fall24.dto.*;
import SWP391.Fall24.dto.Manager.AllRevenueOfMonthDTO;
import SWP391.Fall24.dto.Manager.WeekSalesDTO;
import SWP391.Fall24.dto.Staff.AllOrderDTO;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.OrderStatus;
import jakarta.mail.MessagingException;

import java.util.List;

public interface IOrderService {

    public OrderDTO getOrderDetails(int orderId);
    public int saveOrder(Cart cart, Users user , PlaceOrderDTO placeOrderDTO) throws MessagingException;
    public List<Orders> findOrderByUserId(int userId);
    public List<OrderDetails> getOrderDetailsForStaff(int orderId);
    public List<Orders> getAllOrders();
    public List<Orders> getStaffOrders(int staffId);
    public Orders receiveOrder(int orderId , int staffId);
    public Orders handleOrder(int orderId , OrderStatus status) throws MessagingException;
    public Orders rejectOrder(OrderManagementDTO orderManagementDTO) throws MessagingException;
    public AllOrderDTO getAllOrdersForStaff(int userId);
    //for dashboard
    public AllRevenueOfMonthDTO getOrdersRevenueForDashBoard(int year , int month);
    public List<WeekSalesDTO> getWeeklySales(int year, int month);

}

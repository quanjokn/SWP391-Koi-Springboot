package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.dto.OrderDTO;
import SWP391.Fall24.dto.OrderManagementDTO;
import SWP391.Fall24.pojo.Enum.OrderStatus;
import SWP391.Fall24.pojo.OrderDetails;
import SWP391.Fall24.pojo.Orders;

import SWP391.Fall24.repository.IOrderRepository;
import SWP391.Fall24.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class OrderManagementController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private IOrderRepository  iOrderRepository;

    @GetMapping("/allOrder")
    public List<Orders> getAllOrders() {
        List<Orders> ordersList = orderService.getAllOrders();

        return ordersList;
    }
    @PostMapping("/{staffId}")
    public List<Orders> getStaffOrder(@PathVariable ("staffId") int staffId){
        return orderService.getStaffOrders(staffId);
    }

    @PostMapping("/receiving/{orderId}/{staffId}")
    public String receiveOrder(@PathVariable int orderId, @PathVariable int staffId) {
        Orders orders = orderService.receiveOrder(orderId,staffId);
        return null;
    }

    @PostMapping("/orderDetail/{orderId}")
    public OrderDTO getOrderDetailForStaff(@PathVariable int orderId) {
        OrderDTO orderDetails = orderService.getOrderDetails(orderId);
        return orderDetails;
    }


    @PostMapping("/updateStatus")
    public String updateOrderStatus(@RequestBody OrderManagementDTO orderManagementDTO) {
        String status = orderManagementDTO.getStatus();

        if(status.equals(OrderStatus.Rejected.toString())){
            Orders orders =orderService.rejectOrder(orderManagementDTO);
        }else{
            Orders orders = orderService.handleOrder(orderManagementDTO.getOrderId() ,OrderStatus.valueOf(status));
        }


        return null;
    }


}

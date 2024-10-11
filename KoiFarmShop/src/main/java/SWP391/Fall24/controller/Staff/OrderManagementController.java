package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.pojo.Enum.OrderStatus;
import SWP391.Fall24.pojo.OrderDetails;
import SWP391.Fall24.pojo.Orders;

import SWP391.Fall24.repository.IOrderRepository;
import SWP391.Fall24.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/reveiving/{orderId}/{staffId}")
    public String receiveOrder(@PathVariable int orderId, @PathVariable int staffId) {
        Orders orders = orderService.receiveOrder(orderId,staffId);
        return null;
    }

    @PostMapping("/orderDetail/{orderId}")
    public List<OrderDetails> getOrderDetails(@PathVariable("orderId") int orderId) {
        List<OrderDetails> orderDetailsList = orderService.getUserOrderDetails(orderId);
        return orderDetailsList;
    }


    @PostMapping("/prepareting/{orderId}")
    public String prepareOrder(@PathVariable("orderId") int orderId) {
        Orders orders = orderService.prepareOrder(orderId);
        return null;
    }
    @PostMapping("/accepting/{orderId}")
    public String acceptOrder(@PathVariable("orderId") int orderId) {
        Orders orders = orderService.acceptedOrder(orderId);
        return null;
    }


    @PostMapping("rejected/{orderId}")
    public String rejectedOrder(@PathVariable("orderId") int orderId ) {
        Optional<Orders> opOrder = iOrderRepository.findById(orderId);
        if(opOrder.get().getStatus().equals(OrderStatus.Pending_confirmation.toString())){
            Orders orders = orderService.rejectOrder(orderId);
        }
        return null;
    }
}

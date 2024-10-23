package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.config.VNPAYConfig;
import SWP391.Fall24.dto.OrderDTO;
import SWP391.Fall24.dto.OrderManagementDTO;
import SWP391.Fall24.dto.Staff.AllOrderDTO;
import SWP391.Fall24.pojo.Enum.OrderStatus;
import SWP391.Fall24.pojo.Orders;

import SWP391.Fall24.pojo.Promotions;
import SWP391.Fall24.repository.IOrderRepository;
import SWP391.Fall24.service.OrderService;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderManagementController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private IOrderRepository  iOrderRepository;

    private VNPAYConfig vnpayConfig;

    @PostMapping("/generateOrderId")
    public String generateOrderId() {
        return vnpayConfig.getRandomNumber(8);
    }

    @GetMapping("/allOrder")
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/{staffId}")
    public List<Orders> getStaffOrder(@PathVariable ("staffId") int staffId){
        return iOrderRepository.findByStaffId(staffId);
    }

    @PostMapping("/receiving/{orderId}/{staffId}")
    public String receiveOrder(@PathVariable int orderId, @PathVariable int staffId) {
        orderService.receiveOrder(orderId,staffId);
        return "Receiving successful";
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
            orderService.rejectOrder(orderManagementDTO);
        }else{
            orderService.handleOrder(orderManagementDTO.getOrderId() ,OrderStatus.valueOf(status));
        }
        return null;
    }

    @PostMapping("/getAllOrder/{staffId}")
    public AllOrderDTO getAllOrder(@PathVariable ("staffId") int staffId) {
        AllOrderDTO allOrderDTOList = orderService.getAllOrdersForStaff(staffId);
        return allOrderDTOList;
    }

    @GetMapping("/getPromotion")
    public List<Promotions> getAllPromotion() {
        List<Promotions> promotionList = orderService.getAllPromotions();
        return promotionList;
    }

}

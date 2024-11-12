package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.dto.request.ConsignApprovalRequest;
import SWP391.Fall24.dto.request.ConsignOrderRequest;
import SWP391.Fall24.dto.response.ConsignOrderResponse;
import SWP391.Fall24.pojo.ConsignOrderInvoiceVNPay;
import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.Enum.ConsignOrderStatus;
import SWP391.Fall24.service.ConsignOrderService;
import SWP391.Fall24.service.ConsignOrderVNPayService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consignManagement")
@CrossOrigin(origins = "http://localhost:3000")
public class ConsignOrderManagementController {

    @Autowired
    private ConsignOrderService consignOrderService;

    @GetMapping("/allPendingOrder")
    public List<ConsignOrders> getListConsignOrder() {
        return consignOrderService.findByStatus(ConsignOrderStatus.Pending_confirmation.toString());
    }

    @PostMapping("/receive/{orderID}/{staffID}")
    public String receiveConsignOrder(@PathVariable("orderID") int orderID, @PathVariable("staffID") int staffID) {
        return consignOrderService.receiveConsignOrder(orderID, staffID);
    }

    @PostMapping("/allReceivingOrder/{staffID}")
    public List<ConsignOrders> getAllReceivingOrder(@PathVariable("staffID") int staffID) {
        return consignOrderService.getReceivingOrder(staffID);
    }

    @PostMapping("/approval/{staffID}")
    public String consignOrderApproval(@RequestBody ConsignApprovalRequest consignApprovalRequest, @PathVariable("staffID") int staffID) throws MessagingException {
        return consignOrderService.approvalResponse(consignApprovalRequest, staffID);
    }

    @PostMapping("/detail/{orderID}")
    public ConsignOrderResponse getDetail(@PathVariable("orderID") int orderID) {
        consignOrderService.resolveExpiredOrder();
        return consignOrderService.getDetail(orderID);
    }

    @PostMapping("/done/{staffID}/{orderID}")
    public String completeConsignOrder(@PathVariable("staffID") int staffID, @PathVariable("orderID") int orderID) throws MessagingException {
        return consignOrderService.doneConsignOrder(staffID, orderID);
    }
}

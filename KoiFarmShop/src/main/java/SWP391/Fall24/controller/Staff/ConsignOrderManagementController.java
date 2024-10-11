package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.dto.request.ConsignApprovalRequest;
import SWP391.Fall24.dto.response.ConsignOrderResponse;
import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.Enum.ConsignOrderStatus;
import SWP391.Fall24.service.ConsignOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consignManagement")
public class ConsignOrderManagementController {

    @Autowired
    private ConsignOrderService consignOrderService;

    @GetMapping("/allPendingOrder")
    private List<ConsignOrders> getListConsignOrder() {
        return consignOrderService.findByStatus(ConsignOrderStatus.Pending_confirmation.toString());
    }

    @PostMapping("/receive/{orderID}/{staffID}")
    private String receiveConsignOrder(@PathVariable("orderID") int orderID, @PathVariable("staffID") int staffID) {
        return consignOrderService.receiveConsignOrder(orderID, staffID);
    }

    @PostMapping("/approval/{staffID}")
    private String consignOrderApproval(@RequestBody ConsignApprovalRequest consignApprovalRequest, @PathVariable("staffID") int staffID) {
        return consignOrderService.approvalResponse(consignApprovalRequest, staffID);
    }

    @PostMapping("/detail/{orderID}")
    private ConsignOrderResponse getDetail(@PathVariable("orderID") int orderID) {
        return consignOrderService.getDetail(orderID);
    }
}

package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.dto.request.CareApprovalRequest;
import SWP391.Fall24.dto.response.CaringOrderResponse;
import SWP391.Fall24.pojo.CaringOrders;
import SWP391.Fall24.pojo.Enum.CaringOrderStatus;
import SWP391.Fall24.repository.ICaredKoiRepository;
import SWP391.Fall24.service.CaringOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caringManagement")
@CrossOrigin(origins = "http://localhost:3000")
public class CareOrderManagementController {

    @Autowired
    private ICaredKoiRepository caredKoiRepository;

    @Autowired
    private CaringOrderService caringOrderService;

    @GetMapping("/allPendingOrder")
    public List<CaringOrders> getAllPending(){
        return caringOrderService.getCaringOrdersByStatus(CaringOrderStatus.Pending_confirmation.toString());
    }

    @PostMapping("/allReceivingOrder/{staffID}")
    public List<CaringOrders> getAllReceiving(@PathVariable("staffID") int staffID){
        return caringOrderService.getReceivingOrder(staffID);
    }

    @PostMapping("/detail/{orderID}")
    public CaringOrderResponse detailCaringOrder(@PathVariable("orderID") int orderID) {
        return caringOrderService.getDetail(orderID);
    }

    @PostMapping("/receiving/{staffID}/{orderID}")
    public String receivingCaringOrder(@PathVariable("orderID") int orderID, @PathVariable("staffID") int staffID){
        return caringOrderService.receivingCaringOrder(staffID, orderID);
    }


    @PostMapping("/approval")
    public String approveCaringOrder(@RequestBody CareApprovalRequest approvalRequest) {
        return caringOrderService.approvalCaringOrder(approvalRequest);
    }
}

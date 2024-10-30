package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.dto.request.CareApprovalRequest;
import SWP391.Fall24.dto.request.HealthUpdationRequest;
import SWP391.Fall24.dto.response.CaringOrderResponse;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.CaringOrders;
import SWP391.Fall24.pojo.Enum.CaringOrderStatus;
import SWP391.Fall24.pojo.HealthUpdation;
import SWP391.Fall24.repository.ICaredKoiRepository;
import SWP391.Fall24.repository.ICaringOrderRepository;
import SWP391.Fall24.repository.IHealthUpdationRepository;
import SWP391.Fall24.service.CaringOrderService;
import SWP391.Fall24.service.HealthUpdationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/caringManagement")
@CrossOrigin(origins = "http://localhost:3000")
public class CareOrderManagementController {
    @Autowired
    private CaringOrderService caringOrderService;

    @Autowired
    private ICaringOrderRepository caringOrderRepository;

    @Autowired
    private HealthUpdationService healthUpdationService;

    @Autowired
    private ICaredKoiRepository icaredKoiRepository;

    @Autowired
    private IHealthUpdationRepository healthUpdationRepository;

    @GetMapping("/allPendingOrder")
    public List<CaringOrders> getAllPending(){
        return caringOrderService.getCaringOrdersByStatus(CaringOrderStatus.Pending_confirmation.toString());
    }

    @PostMapping("/allReceivingOrder/{staffID}")
    public List<CaringOrders> getAllReceiving(@PathVariable("staffID") int staffID){
        return caringOrderRepository.findByStaffId(staffID);
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
    public String approveCaringOrder(@RequestBody CareApprovalRequest approvalRequest) throws MessagingException {
        return caringOrderService.approvalCaringOrder(approvalRequest);
    }

    @PostMapping("/complete/{staffID}/{orderID}")
    public String completeCaringOrder(@PathVariable("orderID") int orderID, @PathVariable("staffID") int staffID) {
        return caringOrderService.completeOrder(staffID, orderID);
    }

    @PostMapping("/updateHealthStatus")
    public void updateHealthStatus(@RequestBody HealthUpdationRequest healthUpdationRequest) throws MessagingException {
        healthUpdationService.saveUpdation(healthUpdationRequest);
    }

    @GetMapping("/getAllHealthUpdation/{caredKoiID}")
    public List<HealthUpdation> getAllHealthUpdation(@PathVariable("caredKoiID") int caredKoiID){
        return healthUpdationRepository.findAllByCaredKoi(icaredKoiRepository.findById(caredKoiID).orElseThrow(()-> new AppException(ErrorCode.FISH_NOT_EXISTED)));
    }
}

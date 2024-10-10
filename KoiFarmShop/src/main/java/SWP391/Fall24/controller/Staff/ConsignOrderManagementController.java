package SWP391.Fall24.controller.Staff;

import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.Enum.ConsignOrderStatus;
import SWP391.Fall24.repository.IConsignOrderRepository;
import SWP391.Fall24.service.ConsignOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consignManagement")
public class ConsignOrderManagementController {

    @Autowired
    private ConsignOrderService consignOrderService;

    @Autowired
    private IConsignOrderRepository consignOrderRepository;

    @GetMapping("getListConsignOrder")
    private List<ConsignOrders> getListConsignOrder() {
        return consignOrderRepository.findByStatus(ConsignOrderStatus.Pending_confirmation.toString());
    }

}

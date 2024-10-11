package SWP391.Fall24.controller;

import SWP391.Fall24.dto.request.ConsignOrderRequest;
import SWP391.Fall24.dto.response.ConsignOrderResponse;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.repository.IConsignOrderRepository;
import SWP391.Fall24.repository.IUserRepository;
import SWP391.Fall24.service.ConsignOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consignOrder")
@CrossOrigin(origins = "http://localhost:3000")
public class ConsignOrderController {
    @Autowired
    private ConsignOrderService consignOrderService;

    @Autowired
    private IConsignOrderRepository consignOrderRepository;

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/add/{userId}")
    private ConsignOrderResponse createOrder(@PathVariable("userId") int userId, @RequestBody ConsignOrderRequest consignOrderRequest) {
        return consignOrderService.createOrder(consignOrderRequest, userId);
    }

    @PostMapping("/getList/{userID}")
    private List<ConsignOrders> getList(@PathVariable("userID") int customerID) {
        return consignOrderRepository.findAllByUser(userRepository.findUsersById(customerID)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PostMapping("/detail/{orderID}")
    private ConsignOrderResponse getDetail(@PathVariable("orderID") int orderID) {
        return consignOrderService.getDetail(orderID);
    }

    
}

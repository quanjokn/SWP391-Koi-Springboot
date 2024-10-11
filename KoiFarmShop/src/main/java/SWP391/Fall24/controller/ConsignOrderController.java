package SWP391.Fall24.controller;

import SWP391.Fall24.dto.request.ConsignOrderRequest;
import SWP391.Fall24.service.ConsignOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consignOrder")
@CrossOrigin(origins = "http://localhost:3000")
public class ConsignOrderController {
    @Autowired
    private ConsignOrderService consignOrderService;

    @PostMapping("/add/{userId}")
    private String createOrder(@PathVariable("userId") int userId, @RequestBody ConsignOrderRequest consignOrderRequest) {
        return consignOrderService.createOrder(consignOrderRequest, userId);
    }
}

package SWP391.Fall24.controller;

import SWP391.Fall24.dto.request.CaringOrderRequest;
import SWP391.Fall24.service.CaringOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caringOrder")
@CrossOrigin(origins = "http://localhost:3000")
public class CaringOrderController {

    @Autowired
    private CaringOrderService caringOrderService;

    @PostMapping("/add/{userId}")
    public String addCaringOrder(@RequestBody CaringOrderRequest caringOrderRequest, @PathVariable("userId") int userId) {
        return caringOrderService.createCaringOrder(caringOrderRequest, userId);
    }
}

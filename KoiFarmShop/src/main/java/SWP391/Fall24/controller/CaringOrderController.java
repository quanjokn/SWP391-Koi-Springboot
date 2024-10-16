package SWP391.Fall24.controller;

import SWP391.Fall24.dto.request.CaringOrderRequest;
import SWP391.Fall24.dto.response.CaringOrderResponse;
import SWP391.Fall24.pojo.CaringOrders;
import SWP391.Fall24.repository.ICaringOrderRepository;
import SWP391.Fall24.service.CaringOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/caringOrder")
@CrossOrigin(origins = "http://localhost:3000")
public class CaringOrderController {

    @Autowired
    private CaringOrderService caringOrderService;

    @Autowired
    private ICaringOrderRepository caringOrderRepository;

    @PostMapping("/add/{userId}")
    public String addCaringOrder(@RequestBody CaringOrderRequest caringOrderRequest, @PathVariable("userId") int userId) {
        return caringOrderService.createCaringOrder(caringOrderRequest, userId);
    }

    @PostMapping("/getList/{userID}")
    public List<CaringOrders> getList(@PathVariable("userID") int userID) {
        return caringOrderRepository.findByCustomerId(userID);
    }

    @PostMapping("/detail/{orderID}")
    public CaringOrderResponse detailCaringOrder(@PathVariable("orderID") int orderID) {
        return caringOrderService.getDetail(orderID);
    }

}

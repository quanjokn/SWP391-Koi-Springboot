package SWP391.Fall24.controller;

import SWP391.Fall24.dto.OrderDTO;
import SWP391.Fall24.dto.PlaceOrderDTO;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.Cart;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.ICartRepository;
import SWP391.Fall24.service.OrderService;
import SWP391.Fall24.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/orderDetail")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ICartRepository icartRepository;

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO) throws AppException {
        Optional<Users> u = userService.findByID(placeOrderDTO.getUserId());
        if(u.isPresent()) {
            Users user = u.get();
            Optional<Cart> opCart = icartRepository.findByUserId(placeOrderDTO.getUserId());
            Cart cart = opCart.get();
            int orderId = orderService.saveOrder(cart, user , placeOrderDTO);
            return this.getOrderDetail(orderId);
        } else
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }


    @PostMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderDetail(@PathVariable("orderId") int orderId) {
        OrderDTO orderDTO = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(orderDTO);
    }
}

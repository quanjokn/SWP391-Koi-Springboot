package SWP391.Fall24.controller;

import SWP391.Fall24.dto.Cart;
import SWP391.Fall24.dto.Item;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.IOrderService;
import SWP391.Fall24.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private Cart cart = new Cart();
    @Autowired
    private UserService userService;

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody Item item) {
        cart.addItem(item);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/updateCart")
    public ResponseEntity<Cart> updateCart(@RequestBody Item item) {
        cart.update(item);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/remove")
    public ResponseEntity<Cart> removeItem(@RequestBody Item item) {
        cart.removeItem(item);
        return ResponseEntity.ok(cart);
    }

    @Autowired
    private IOrderService iOrderService;

    @PostMapping("/order")
    public ResponseEntity<Orders> placeOrder(@RequestBody Cart cart,@RequestParam int userId) {
        Users user = userService.findByID(userId);
        Orders saveOrder = iOrderService.saveOrder(cart, user);
        return ResponseEntity.ok(saveOrder);
    }
}

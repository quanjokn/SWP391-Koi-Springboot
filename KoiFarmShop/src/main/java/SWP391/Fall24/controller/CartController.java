package SWP391.Fall24.controller;

import SWP391.Fall24.dto.Cart;
import SWP391.Fall24.dto.Item;
import SWP391.Fall24.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    private Cart cart = new Cart();

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
        cart.removeItem(item.getFishid());
        return ResponseEntity.ok(cart);
    }

    @Autowired
    private IOrderService iOrderService;

    @PostMapping("/order")
    public Cart placeOrder() {
        iOrderService.saveOrder(cart);
        cart = new Cart();
        return cart;
    }
}

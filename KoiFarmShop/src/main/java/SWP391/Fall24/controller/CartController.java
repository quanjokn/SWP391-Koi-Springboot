package SWP391.Fall24.controller;

import SWP391.Fall24.dto.CartDTO;
import SWP391.Fall24.dto.CartItemDTO;
import SWP391.Fall24.pojo.Orders;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.CartService;
import SWP391.Fall24.service.IOrderService;
import SWP391.Fall24.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable int userId) {
        CartDTO cart = cartService.findCartByUserId(userId); // Giả sử bạn có một service để lấy giỏ hàng
        if (cart == null) {
            // Nếu giỏ hàng chưa tồn tại cho user này, tạo mới một CartDTO
            cart = new CartDTO();
            cart.setCartItems(new ArrayList<>());  // Khởi tạo danh sách rỗng cho cartItems
            cart.setTotalPrice(0);  // Đặt giá trị tổng tiền ban đầu
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/addToCart/{userId}")
    public ResponseEntity<CartDTO> addToCart(@RequestBody CartItemDTO cartItemDTO, @PathVariable int userId) {
        CartDTO updatedCart = cartService.addToCart(cartItemDTO, userId);
        return ResponseEntity.ok(updatedCart);
    }

    @PutMapping("/update")
    public ResponseEntity<CartDTO> updateCartItem(@RequestParam int userId, @RequestBody CartItemDTO cartItemDTO) {
        CartDTO updatedCart = cartService.updateCartItem(userId, cartItemDTO.getFishId(), cartItemDTO.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/remove/{fishId}")
    public ResponseEntity<CartDTO> removeFromCart(@RequestParam int userId, @PathVariable int fishId) {
        CartDTO updatedCart = cartService.removeFromCart(userId, fishId);
        return ResponseEntity.ok(updatedCart);
    }


    @Autowired
    private IOrderService iOrderService;

    @PostMapping("/order")
    public ResponseEntity<Orders> placeOrder(@RequestBody CartDTO cart, @RequestParam int userId) {
        Optional<Users> user = userService.findByID(userId);
        Orders saveOrder = iOrderService.saveOrder(cart, user.orElse(null));
        return ResponseEntity.ok(saveOrder);
    }
}

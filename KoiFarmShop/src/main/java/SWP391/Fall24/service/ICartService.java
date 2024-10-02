package SWP391.Fall24.service;

import SWP391.Fall24.dto.CartDTO;
import SWP391.Fall24.dto.CartItemDTO;

public interface ICartService {
    public CartDTO addToCart(CartItemDTO cartItemDTO, int userId);
    public CartDTO removeFromCart(int userId, int fishId);
    public CartDTO updateCartItem(int userId, int fishId, int quantity);
}

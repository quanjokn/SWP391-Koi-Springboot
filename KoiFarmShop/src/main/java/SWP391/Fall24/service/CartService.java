package SWP391.Fall24.service;

import SWP391.Fall24.dto.CartDTO;
import SWP391.Fall24.dto.CartItemDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.Cart;
import SWP391.Fall24.pojo.CartItem;
import SWP391.Fall24.pojo.ConsignedKois;
import SWP391.Fall24.repository.ICartItemRepository;
import SWP391.Fall24.repository.ICartRepository;
import SWP391.Fall24.repository.IConsignedKoiRepository;
import SWP391.Fall24.repository.IFishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService implements ICartService {
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private IFishRepository fishRepository;
    @Autowired
    private FishService fishService;
    @Autowired
    private IConsignedKoiRepository consignedKoiRepository;

    public CartDTO addToCart(CartItemDTO cartItemDTO, int userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());
        cart.setUser(userService.findByID(userId).orElseThrow(() -> new RuntimeException("User not found")));

        // Kiểm tra nếu cartItems là null
        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());  // Khởi tạo danh sách trống nếu chưa có
        }

        FishDetailDTO fishDetail = fishService.findFishDetailByFishId(cartItemDTO.getFishId())
                .orElseThrow(() -> new RuntimeException("FishDetail not found"));

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getFish().getId() == fishDetail.getId())
                .findFirst();
        List<ConsignedKois> consignedKoi = consignedKoiRepository.findAll();
        for(ConsignedKois koi: consignedKoi) {
            if(koi.getFish().getId()==fishDetail.getId() && koi.getCustomerID()==userId) throw new AppException(ErrorCode.CONSIGNED_FISH_OWNER);
        }
        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
            cartItem.setTotalPrice(cartItem.getUnitPrice() * cartItem.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setFish(fishRepository.findById(fishDetail.getId()).orElseThrow(() -> new RuntimeException("Fish not found")));
            cartItem.setUnitPrice(fishDetail.getPrice());
            cartItem.setTotalPrice(fishDetail.getPrice() * cartItemDTO.getQuantity());
            cartItem.setCart(cart);
            cartItem.setPhoto(fishDetail.getPhoto());
            cart.getCartItems().add(cartItem);
        }

        // Cập nhật tổng giá giỏ hàng
        float totalPrice = (float) cart.getCartItems().stream()
                .mapToDouble(CartItem::getTotalPrice).sum();
        cart.setTotalPrice(totalPrice);

        // Lưu giỏ hàng và các sản phẩm
        cartRepository.save(cart);

        // Trả về DTO để gửi lại client
        return convertToCartDTO(cart);
    }

    public CartDTO removeFromCart(int userId, int fishId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> updatedCartItems = cart.getCartItems().stream()
                .filter(item -> item.getFish().getId() != fishId)
                .collect(Collectors.toList());

        // Cập nhật danh sách cartItems
        cart.getCartItems().clear();
        cart.getCartItems().addAll(updatedCartItems);

        // Cập nhật tổng giá sau khi xóa
        float totalPrice = (float) updatedCartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
        cart.setTotalPrice(totalPrice);

        // Lưu giỏ hàng
        cartRepository.save(cart);

        return convertToCartDTO(cart);
    }

    public CartDTO updateCartItem(int userId, int fishId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));

        Optional<CartItem> cartItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getFish().getId() == fishId)
                .findFirst();

        if (cartItemOpt.isPresent()) {
            CartItem cartItem = cartItemOpt.get();
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(cartItem.getUnitPrice() * cartItem.getQuantity());

            // Cập nhật tổng giá giỏ hàng
            float totalPrice = (float) cart.getCartItems().stream()
                    .mapToDouble(CartItem::getTotalPrice).sum();
            cart.setTotalPrice(totalPrice);

            // Lưu giỏ hàng
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Item not found in cart");
        }

        return convertToCartDTO(cart);
    }

    @Override
    public Cart getCart(int userId) {
        return cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }


    public CartDTO convertToCartDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setTotalPrice((float) cart.getTotalPrice());

        List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream().map(item -> {
            CartItemDTO dto = new CartItemDTO();
            dto.setFishId(item.getFish().getId());
            dto.setFishName(fishService.findFishDetailByFishId(item.getFish().getId()).orElseThrow(() -> new RuntimeException("Fish not found")).getName());
            dto.setQuantity(item.getQuantity());
            dto.setUnitPrice((float) item.getUnitPrice());
            dto.setTotalPrice((float) item.getTotalPrice());
            dto.setPhoto(item.getPhoto());
            return dto;
        }).collect(Collectors.toList());

        cartDTO.setCartItems(cartItemDTOs);
        return cartDTO;
    }

    public CartDTO findCartByUserId(int userId) {
        // Tìm giỏ hàng theo userId từ cơ sở dữ liệu
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("cart not found"));

        // Chuyển đổi Cart entity thành CartDTO
        if (cart != null) {
            return convertToCartDTO(cart);
        }

        // Nếu giỏ hàng không tồn tại, trả về null
        return null;
    }

}

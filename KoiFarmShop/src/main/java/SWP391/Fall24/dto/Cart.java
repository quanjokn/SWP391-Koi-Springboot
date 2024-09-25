package SWP391.Fall24.dto;

import SWP391.Fall24.pojo.Fishes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Map<Integer, Item> cart = new HashMap<>();

    // add product
    public void addItem(Item item) {
        int fishId = item.getFish();
        // Kiểm tra nếu sản phẩm đã tồn tại trong giỏ hàng
        if (cart.containsKey(fishId)) {
            Item existingItem = cart.get(fishId);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            cart.put(fishId, item);
        }
    }

    // delete item
    public void removeItem(Item item) {
        // Lấy ID cá từ item
        int fishId = item.getFish();

        // Xóa item nếu tồn tại trong giỏ hàng
        if (cart.containsKey(fishId)) {
            cart.remove(fishId);
        }
    }

    // update item
    public void update(Item item) {
        // Lấy ID cá từ item
        int fishId = item.getFish();

        // Kiểm tra xem item có tồn tại trong giỏ hàng không
        if (cart.containsKey(item.getFish())) {
            // Cập nhật số lượng
            cart.get(fishId).setQuantity(item.getQuantity());
        }
    }
}

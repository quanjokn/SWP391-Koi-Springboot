package SWP391.Fall24.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Map<Integer, Item> cart;

    // add product
    public void addItem(Item item) {
        for(Map.Entry<Integer, Item> c : cart.entrySet()) {
            if(c.getValue().getFishid() == item.getFishid()) {
                c.getValue().setQuantity(c.getValue().getQuantity() + item.getQuantity());
            }
        }
        cart.put(item.getFishid(), item);
    }

    // delete item
    public void removeItem(Item item) {
        for(Map.Entry<Integer, Item> c : cart.entrySet()) {
            if(c.getValue().getFishid() == item.getFishid()) {
                cart.remove(item.getFishid());
            }
        }
    }

    // update item
    public void update(Item item) {
        for(Map.Entry<Integer, Item> c : cart.entrySet()) {
            if(c.getValue().getFishid() == item.getFishid()) {
                c.getValue().setQuantity(c.getValue().getQuantity() - item.getQuantity());
            }
        }
    }
}

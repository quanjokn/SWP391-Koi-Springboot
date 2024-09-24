package SWP391.Fall24.dto;

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
        boolean check = false;
        for(Item c: cart.values()) {
            if(c.getFishid() == item.getFishid()) {
                c.setQuantity(c.getQuantity() + item.getQuantity());
                check = true;
                break;
            }
        }
        if(!check) {
            cart.put(item.getFishid(), item);
        }
    }

    // delete item
    public void removeItem(int fishid) {
        for(Item c: cart.values()) {
            if(c.getFishid() == fishid) {
                cart.remove(fishid);
            }
        }
    }

    // update item
    public void update(Item item) {
        for(Item c: cart.values()) {
            if(c.getFishid() == item.getFishid()) {
                c.setQuantity(item.getQuantity());
            }
        }
    }
}

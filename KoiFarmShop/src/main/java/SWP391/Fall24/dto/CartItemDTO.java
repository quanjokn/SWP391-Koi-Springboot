package SWP391.Fall24.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private int fishId;
    private String fishName;
    private int quantity;
    private float unitPrice;
    private float totalPrice;
    private String photo;

    public CartItemDTO(int fishId, int quantity, float unitPrice, float totalPrice) {
        this.fishId = fishId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}

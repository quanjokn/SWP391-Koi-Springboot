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
}

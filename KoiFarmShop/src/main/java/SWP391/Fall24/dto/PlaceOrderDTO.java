package SWP391.Fall24.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderDTO {
    private int userId;
    private String paymentMethod;
}

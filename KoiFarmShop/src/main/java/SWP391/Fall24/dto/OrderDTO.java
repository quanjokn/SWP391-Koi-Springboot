package SWP391.Fall24.dto;

import SWP391.Fall24.pojo.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private int id;
    private float totalOrderPrice;
    private int totalQuantity;
    private LocalDate date;
    private OrderStatus status;
    @JsonProperty("orderDetailsDTO")
    private List<OrderDetailsDTO> orderDetailsDTO;

}

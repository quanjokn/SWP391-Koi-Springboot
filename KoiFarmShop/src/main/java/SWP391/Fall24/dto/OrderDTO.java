package SWP391.Fall24.dto;

import SWP391.Fall24.pojo.OrderDateStatus;
import SWP391.Fall24.pojo.Users;
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

    private int orderId;
    private Users users;
    private float totalOrderPrice;
    private int totalQuantity;
    private LocalDate date;
    private String status ;
    private String note;
    @JsonProperty("orderDetailsDTO")
    private List<OrderDetailsDTO> orderDetailsDTO;
    @JsonProperty
    private OrderDateStatus orderDateStatus;

}

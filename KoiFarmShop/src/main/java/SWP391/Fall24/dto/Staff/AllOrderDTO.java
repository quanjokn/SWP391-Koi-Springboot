package SWP391.Fall24.dto.Staff;

import SWP391.Fall24.pojo.CaringOrders;
import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.Orders;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllOrderDTO {
//    @JsonProperty("orders")
    public List<Orders> order;
//    @JsonProperty("consignOrders")
    public List<ConsignOrders> consignOrders;
//    @JsonProperty("caringOrders")
    public List<CaringOrders> caringOrders;
}

package SWP391.Fall24.dto.Staff;

import SWP391.Fall24.pojo.CaringOrders;
import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.Orders;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllOrderDTO {
    public List<Orders> order;
    public List<ConsignOrders> consignOrders;
    public List<CaringOrders> caringOrders;
}

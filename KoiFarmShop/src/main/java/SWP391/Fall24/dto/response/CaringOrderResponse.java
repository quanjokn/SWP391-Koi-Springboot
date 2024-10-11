package SWP391.Fall24.dto.response;

import SWP391.Fall24.pojo.CaredKois;
import SWP391.Fall24.pojo.CaringOrders;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaringOrderResponse {
    private CaringOrders caringOrder;
    private List<CaredKois> caredKois;
}

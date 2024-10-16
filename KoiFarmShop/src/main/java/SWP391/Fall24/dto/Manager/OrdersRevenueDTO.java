package SWP391.Fall24.dto.Manager;

import lombok.*;

import java.math.BigDecimal;


@Data
@Setter
@Getter
public class OrdersRevenueDTO {
    private int weekofMonth;
    private int totalOrders;
    private Double totalRevenue;

    public OrdersRevenueDTO(int weekofMonth, int totalOrders, Double totalRevenue) {
        this.weekofMonth = weekofMonth;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
    }
}

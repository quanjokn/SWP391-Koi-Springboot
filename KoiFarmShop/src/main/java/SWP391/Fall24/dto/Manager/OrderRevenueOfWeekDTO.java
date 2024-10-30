package SWP391.Fall24.dto.Manager;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class OrderRevenueOfWeekDTO {
    private int weekofMonth;
    private int totalOrders;
    private Double totalRevenueOfWeek;

    public OrderRevenueOfWeekDTO(int weekofMonth, int totalOrders, Double totalRevenueOfWeek) {
        this.weekofMonth = weekofMonth;
        this.totalOrders = totalOrders;
        this.totalRevenueOfWeek = totalRevenueOfWeek;
    }
}

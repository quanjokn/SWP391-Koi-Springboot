package SWP391.Fall24.dto.Manager;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Setter
@Getter
public class AllRevenueOfMonthDTO {
    private double allRevenueOfMonth;
    private List<OrderRevenueOfWeekDTO> ordersRevenueList;

    public AllRevenueOfMonthDTO(double allRevenueOfMonth, List<OrderRevenueOfWeekDTO> ordersRevenueList) {
        this.allRevenueOfMonth = allRevenueOfMonth;
        this.ordersRevenueList = ordersRevenueList;
    }
}

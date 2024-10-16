package SWP391.Fall24.dto.Manager;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Setter
@Getter
public class WeekSalesDTO {
    private int weekOfMonth;
    private List<ProductSalesDTO> productSalesDTOList;

    public WeekSalesDTO(int weekOfMonth, List<ProductSalesDTO> productSalesDTOList) {
        this.weekOfMonth = weekOfMonth;
        this.productSalesDTOList = productSalesDTOList;
    }
}

package SWP391.Fall24.dto.Manager;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Setter
@Getter
public class MonthSalesDTO {
    private int month;
    private List<ProductSalesDTO> productSalesDTOList;

    public MonthSalesDTO(int month, List<ProductSalesDTO> productSalesDTOList) {
        this.month = month;
        this.productSalesDTOList = productSalesDTOList;
    }
}

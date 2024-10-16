package SWP391.Fall24.dto.Manager;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProductSalesDTO {
    private String fishName;
    private int totalQuantity;

    public ProductSalesDTO(String fishName, int totalQuantity) {
        this.fishName = fishName;
        this.totalQuantity = totalQuantity;
    }
}

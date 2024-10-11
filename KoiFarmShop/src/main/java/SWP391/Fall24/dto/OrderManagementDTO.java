package SWP391.Fall24.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderManagementDTO {
    private int orderId ;
    private String status;
    private String note ;
}

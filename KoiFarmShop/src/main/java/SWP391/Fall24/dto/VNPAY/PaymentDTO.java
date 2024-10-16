package SWP391.Fall24.dto.VNPAY;

import lombok.Data;

@Data
public class PaymentDTO {
    private String status;
    private String message;
    private String URL;
}

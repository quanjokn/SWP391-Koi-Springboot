package SWP391.Fall24.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignApprovalRequest {
    private int orderID;
    private String response; // Accepted or Rejected
    private String note;
}

package SWP391.Fall24.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignApprovalRequest {
    private int orderID;
    private HashMap<Integer, Boolean> decision; // Integer : fishID, Boolean is decision
    private String note;
}

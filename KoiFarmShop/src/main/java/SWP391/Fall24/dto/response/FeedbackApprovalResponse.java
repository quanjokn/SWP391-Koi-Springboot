package SWP391.Fall24.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackApprovalResponse {
    private String images;
    private String fishName;
    private String userName;
    private float Rating;
    private String feedback;
    private int orderId;
    private int fishId;
}

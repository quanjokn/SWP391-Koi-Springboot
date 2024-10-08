package SWP391.Fall24.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    private String userName;
    private LocalDate feedbackDate;
    private float rating;
    private String feedback;
}

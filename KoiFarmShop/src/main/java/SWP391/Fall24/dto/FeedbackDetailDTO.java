package SWP391.Fall24.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDetailDTO {
    private String feedback;
    private float rating;
}

package SWP391.Fall24.dto;

import SWP391.Fall24.pojo.Evaluations;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    @JsonProperty("evaluation")
    private List<Evaluations> evaluation;
}

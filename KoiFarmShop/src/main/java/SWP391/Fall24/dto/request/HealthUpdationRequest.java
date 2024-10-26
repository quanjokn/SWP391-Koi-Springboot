package SWP391.Fall24.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthUpdationRequest {
    private int caredKoiID;
    private LocalDate date;
    private String photo;
    private String evaluation;
}

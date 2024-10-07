package SWP391.Fall24.dto.request;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignOrderRequest {
    private LocalDate date;
    private float totalPrice;
    private float commission;

    @JsonProperty("ConsignList")
    private List<ConsignedKoiDTO> ConsignList;
}

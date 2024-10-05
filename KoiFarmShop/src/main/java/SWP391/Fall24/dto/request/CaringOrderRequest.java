package SWP391.Fall24.dto.request;

import SWP391.Fall24.dto.CaredKoiDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaringOrderRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private float totalPrice;

    @JsonProperty("CaredKoiList")
    private List<CaredKoiDTO> CaredKoiList;
}

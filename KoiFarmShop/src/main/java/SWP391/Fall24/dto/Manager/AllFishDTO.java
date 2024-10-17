package SWP391.Fall24.dto.Manager;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.pojo.CaredKois;
import SWP391.Fall24.pojo.CaringOrders;
import SWP391.Fall24.pojo.ConsignedKois;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AllFishDTO {
    private List<FishDetailDTO> fishDetailDTO;
    private List<ConsignedKois> consignedKois;
    private List<CaredKois> caredKois;
}

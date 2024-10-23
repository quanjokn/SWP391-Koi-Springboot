package SWP391.Fall24.dto;

import SWP391.Fall24.pojo.Promotions;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FishAndPromotionDTO {
    private List<FishDetailDTO> fishDetailDTOList;
    private List<Promotions> promotionsList;
}

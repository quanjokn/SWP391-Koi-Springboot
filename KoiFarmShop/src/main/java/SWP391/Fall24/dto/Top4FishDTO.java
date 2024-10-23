package SWP391.Fall24.dto;

import lombok.*;


@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Top4FishDTO {
    private FishDetailDTO fishDetailDTO;
    private int totalQuantity;
}

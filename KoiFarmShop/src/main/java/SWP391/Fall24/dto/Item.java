package SWP391.Fall24.dto;

import SWP391.Fall24.pojo.Fishes;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private int fish;
    private int quantity;
    private int price;
}

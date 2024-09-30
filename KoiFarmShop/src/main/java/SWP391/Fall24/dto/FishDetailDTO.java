package SWP391.Fall24.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FishDetailDTO {
    private int id;
    private String name;
    private int quantity;
    private String description;
    private String sex;
    private String age;
    private String character;
    private String size;
    private float price;
    private String healthStatus;
    private String ration;
    private String photo;
    private String video;
    private String certificate;
    private String category;
    private float discount = 0;
    private float promotionPrice = price * (1-discount);
    private String origin;

}

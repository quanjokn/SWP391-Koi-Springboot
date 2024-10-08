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

    @JsonProperty("evaluation")
    private List<Evaluations> evaluation;

    public FishDetailDTO(int id, String name, int quantity, String description, String sex, String age, String character, String size, float price, String healthStatus, String ration, String photo, String video, String certificate, String category, float discount, float promotionPrice, String origin) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.sex = sex;
        this.age = age;
        this.character = character;
        this.size = size;
        this.price = price;
        this.healthStatus = healthStatus;
        this.ration = ration;
        this.photo = photo;
        this.video = video;
        this.certificate = certificate;
        this.category = category;
        this.discount = discount;
        this.promotionPrice = promotionPrice;
        this.origin = origin;
    }
}

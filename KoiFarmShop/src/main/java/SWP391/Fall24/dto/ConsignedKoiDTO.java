package SWP391.Fall24.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignedKoiDTO {
    private int fishID;
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
    private int origin;

    // ConsignedKoiStatus
    private String status;

    @JsonProperty("species")
    private Set<String> species;

    public ConsignedKoiDTO(Set species) {
        this.species = species;
    }

    public ConsignedKoiDTO(int fishID, String name, String description, int quantity, String sex, String age, String character, float price, String size, String healthStatus, String ration, String photo, String video, String certificate, String category, float discount, float promotionPrice, int origin, String status) {
        this.fishID = fishID;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.sex = sex;
        this.age = age;
        this.character = character;
        this.price = price;
        this.size = size;
        this.healthStatus = healthStatus;
        this.ration = ration;
        this.photo = photo;
        this.video = video;
        this.certificate = certificate;
        this.category = category;
        this.discount = discount;
        this.promotionPrice = promotionPrice;
        this.origin = origin;
        this.status = status;
    }
}
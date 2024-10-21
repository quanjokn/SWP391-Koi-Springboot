package SWP391.Fall24.dto;

import lombok.*;


@Setter
@Getter
@NoArgsConstructor
public class OrderDetailsDTO {
    private int fishId;
    private String fishName;
    private int quantity;
    private float unitPrice;
    private float totalPrice;
    private String photo;
    private Boolean evaluationStatus;

    public OrderDetailsDTO(int fishId, String fishName, int quantity, float unitPrice , float totalPrice , String photo, boolean evaluationStatus) {
        this.fishId = fishId;
        this.fishName = fishName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.photo = photo;
        this.evaluationStatus = evaluationStatus;
    }
}

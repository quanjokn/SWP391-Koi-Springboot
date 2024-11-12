package SWP391.Fall24.dto.response;

import SWP391.Fall24.dto.request.ConsignOrderRequest;
import SWP391.Fall24.pojo.ConsignDateStatus;
import SWP391.Fall24.pojo.Users;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignOrderResponse {

    private int orderID;

    //ConsignOrderStatus
    private String status;

    @JsonProperty("customer")
    private Users customer;

    @JsonProperty
    private ConsignDateStatus consignDateStatus;

    private ConsignOrderRequest request;
}

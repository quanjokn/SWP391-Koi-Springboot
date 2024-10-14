package SWP391.Fall24.dto.Manager;

import SWP391.Fall24.pojo.Users;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
//    @JsonProperty("customers")
    private List<Users> customers;
//    @JsonProperty("staff")
    private List<Users> staff;
}

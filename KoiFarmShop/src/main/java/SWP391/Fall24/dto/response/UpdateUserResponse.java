package SWP391.Fall24.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponse {
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
}

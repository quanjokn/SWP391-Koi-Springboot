package SWP391.Fall24.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
    private String email;
    private String userName;
    private String password;
    private String confirmPassword;
}

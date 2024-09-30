package SWP391.Fall24.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRegistrationUser {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 100)
    private String userName;

    @NotNull
    private String password;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$")
    private String phone;
}

package SWP391.Fall24.controller;

import SWP391.Fall24.dto.request.ChangePasswordRequest;
import SWP391.Fall24.dto.request.LoginRequest;
import SWP391.Fall24.dto.request.RequestRegistrationUser;
import SWP391.Fall24.dto.request.UpdateUserRequest;
import SWP391.Fall24.dto.response.ApiResponse;
import SWP391.Fall24.dto.response.LoginResponse;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<Users> registerUser(@Valid @RequestBody RequestRegistrationUser requestRegistrationUser) {
        return ApiResponse.<Users>builder()
                .result(userService.registerUser(requestRegistrationUser))
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        String jwt = userService.loginUser(loginRequest);
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/updateUser/{id}")
    public Users update(@PathVariable int id, @RequestBody UpdateUserRequest u) {
        return userService.updateUsers(id, u);
    }

    @GetMapping("/profile")
    public Users getLoggedInUser(@AuthenticationPrincipal Users user) {
        return user;
    }

    @PostMapping("/changePassword/{id}")
    public Users changePassword(@PathVariable("id") int id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(id, changePasswordRequest);
    }
}

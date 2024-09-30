package SWP391.Fall24.controller;


import SWP391.Fall24.dto.request.LoginRequest;
import SWP391.Fall24.dto.request.RequestRegistrationUser;
import SWP391.Fall24.dto.response.ApiResponse;
import SWP391.Fall24.dto.response.LoginResponse;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<Users> registerUser(@Valid @RequestBody RequestRegistrationUser requestRegistrationUser) {
        return ApiResponse.<Users>builder()
                .result(userService.registerUser(requestRegistrationUser))
                .build();
    }

//    @PostMapping("/login")
//    public ResponseEntity<Users> login(@RequestBody Users user) {
//        if(userService.getUser(user.getUserName(), user.getPassword()) != null) {
//            return ResponseEntity.ok(userService.getUser(user.getUserName(), user.getPassword()));
//        }
//        return null;
//    }

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


    @GetMapping("/getAllUser")
    public ResponseEntity<List<Users>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/deleteUser/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "Delete user successfully";
    }

//    @PostMapping("/updateUser/{id}")
//    public Users update(@PathVariable int id, @RequestBody Users user) {
//        return userService.updateUsers(id, user);
//    }

    /**
     * Gets the profile of the currently logged-in user and returns it.
     * @param user The authentication principal object.
     * @return The user profile.
     */
    @GetMapping("/profile")
    public Users getLoggedInUser(@AuthenticationPrincipal Users user) {
        return user;
    }
}

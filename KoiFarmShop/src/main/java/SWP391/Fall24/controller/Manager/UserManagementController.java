package SWP391.Fall24.controller.Manager;

import SWP391.Fall24.dto.Manager.UserResponseDTO;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userManagement")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class UserManagementController {

    @Autowired
    private UserService userService;

    // include all customer and staff
    @GetMapping("/allAccount")
    public UserResponseDTO getAllAccount(){
        UserResponseDTO userResponseDTO = userService.getAllAccount();
        return userResponseDTO;
    }

    @DeleteMapping("/deleteUser/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "Delete user successfully";
    }

    @PostMapping("/profile/{userId}")
    public Optional<Users> findUser(@PathVariable int userId) {
        return userService.findByID(userId);
    }

    @PostMapping("/createAccount")
    public Users createAccount(@RequestBody Users user) {
        return userService.createUser(user);
    }
}

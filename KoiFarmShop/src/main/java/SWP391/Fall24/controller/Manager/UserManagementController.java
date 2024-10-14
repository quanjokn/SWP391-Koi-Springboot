package SWP391.Fall24.controller.Manager;

import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userManagement")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class UserManagementController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<List<Users>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/deleteUser/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Role: {}", authentication.getAuthorities());
        return "Delete user successfully";
    }
}

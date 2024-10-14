package SWP391.Fall24.controller.Manager;


import SWP391.Fall24.dto.Manager.UserResponseDTO;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/allAccount")
    public UserResponseDTO getAllAccount(){
        UserResponseDTO userResponseDTO = userService.getAllAccount();
        return userResponseDTO;
    }

    @PostMapping("/createAccount")
    public void createAccount(@RequestBody Users user) {
        Users newUsers = userService.createUser(user);
    }

    @PutMapping("/updateUser/{userId}")
    public Users updateUser(@PathVariable int userId, @RequestBody Users user) {
       return userService.updateUserForManager(userId,user);

    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
    }

    //cai nay cho manager de lay user bang id nha
    @PostMapping("/findUser/{userId}")
    public Optional<Users> findUser(@PathVariable int userId) {
        return userService.findByID(userId);
    }
}

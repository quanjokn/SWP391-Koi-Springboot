package SWP391.Fall24.controller;


import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    // function: add user into db
    @PostMapping("/addUser")
    public String add(@RequestBody Users user) {
        userService.saveUsers(user);
        return "Add user successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users user) {
        if(userService.getUser(user.getUserName(), user.getPassword()) != null) {
            return ResponseEntity.ok(userService.getUser(user.getUserName(), user.getPassword()));
        }
        return null;
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

//    @PostMapping("/updateUser")
//    public String update(@RequestBody Users user) {
//
//    }

}

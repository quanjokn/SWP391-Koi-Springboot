package SWP391.Fall24.controller;


import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/login/{username}/{password}")
    @ResponseBody
    public String login(@PathVariable String username, @PathVariable String password) {
        if(userService.getUser(username, password) != null) {
            return "Login successfully";
        }
        return "Login failed";
    }

    @GetMapping("/getAllUser")
    public List<Users> getAll() {
        return userService.getAllUsers();
    }

    @PostMapping("/deleteUser/{id}")
    @ResponseBody
    public String delete(@PathVariable int id) {
        userService.deleteUser(id);
        return "Delete user successfully";
    }

//    @PostMapping("/updateUser")
//    public String update(@RequestBody Users user) {
//
//    }

}

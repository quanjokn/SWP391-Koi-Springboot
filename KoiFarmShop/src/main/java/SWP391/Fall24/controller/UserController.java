package SWP391.Fall24.controller;


import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // function: add user into db
    @PostMapping("/add")
    public String add(@RequestBody Users user) {
        userService.saveUsers(user);
        return "Add user successfully";
    }

}

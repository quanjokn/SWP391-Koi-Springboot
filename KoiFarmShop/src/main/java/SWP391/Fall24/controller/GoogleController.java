package SWP391.Fall24.controller;

//import SWP391.Fall24.pojo.Users;
//import SWP391.Fall24.service.GoogleService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;

//@Controller
//@CrossOrigin(origins = "http://localhost:3000")
//public class GoogleController {
//    @Autowired
//    GoogleService googleService;
//
//    @GetMapping("/loginGoogle")
//    public String loginGoogle() {
//        return "redirect:http://localhost:8080/oauth2/authorization/google";
//    }
//
//    @GetMapping("/save")
//    public String saveUser(OAuth2AuthenticationToken auth){
//        googleService.saveUsersByEmail(auth);
//        return "redirect:http://localhost:3000";
//    }
//}

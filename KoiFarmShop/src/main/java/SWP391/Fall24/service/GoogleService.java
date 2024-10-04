//package SWP391.Fall24.service;
//
//import SWP391.Fall24.pojo.Role;
//import SWP391.Fall24.pojo.Users;
//import SWP391.Fall24.repository.IGoogleRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//@Service
//public class GoogleService implements IGoogleService {
//
//    @Autowired
//    IGoogleRepository iGoogleRepository;
//    @Override
//    public Users saveUsersByEmail(OAuth2AuthenticationToken auth) {
//        String email = auth.getPrincipal().getAttribute("email");
//        String name = auth.getPrincipal().getAttribute("name");
//
//        Optional<Users> existUser = iGoogleRepository.findByEmail(email);
//
//        if(existUser.isEmpty()){
//            Users u = new Users();
//            u.setEmail(email);
//            u.setName(name);
//            u.setUserName(email);
//            u.setPassword("");
//            u.setRole(Role.Customer);
//            u.setPhone("");
//            u.setStatus(true);
//
//            return iGoogleRepository.save(u);
//        }
//        return existUser.get();
//    }
//}


package SWP391.Fall24.controller;

import SWP391.Fall24.dto.request.ForgotPasswordRequest;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.service.EmailService;
import SWP391.Fall24.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.random.RandomGenerator;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "http://localhost:3000")
public class EmailController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/forgotPassword")
    public String forgotPasswordEmail(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        Optional<Users> user = userService.findByUserNameAndEmailIgnoreCase(forgotPasswordRequest.getUserName(), forgotPasswordRequest.getEmail());
        if(user.isPresent()) {
            int code = RandomGenerator.getDefault().nextInt(100000, 999999);
            Users u = user.get();
            String name = u.getName();
            String email = u.getEmail();
            emailService.sendMail(email, emailService.subjectForgotPass(), emailService.messageForgotPass(name, code));
            return "Send email successfully";
        } else{
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
    }

}

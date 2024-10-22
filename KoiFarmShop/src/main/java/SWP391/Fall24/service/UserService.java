package SWP391.Fall24.service;

import SWP391.Fall24.dto.Manager.UserResponseDTO;
import SWP391.Fall24.dto.request.*;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.Enum.Role;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    private EncryptionService encryptionService = new EncryptionService();

    @Autowired
    private JWTService jwtService = new JWTService();

    @Override
    public Optional<Users> findByID(int id) {

        return Optional.ofNullable(iUserRepository.findById(id));
    }

    @Override
    public String loginUser(LoginRequest loginRequest) {
        Optional<Users> opUser = iUserRepository.findByUserNameIgnoreCase(loginRequest.getUserName());
        if (opUser.isPresent()) {
            Users user = opUser.get();
            if (encryptionService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
                return jwtService.generateJWT(user);
            }
        }else
            throw new AppException(ErrorCode.FAIL_LOGIN);
        return null;
    }

    @Override
    public Users changePassword(int id, ChangePasswordRequest changePasswordRequest) {
        Users user = iUserRepository.findById(id);
        boolean verifyPassword = encryptionService.verifyPassword(changePasswordRequest.getOldPassword(), user.getPassword());
        if(!verifyPassword) {
            throw new AppException(ErrorCode.PASSWORD_ERROR);
        }
        boolean comparePasword = changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword());
        if(!comparePasword) {
            throw new AppException(ErrorCode.CONFIRMED_PASSWORD_ERROR);
        }
        if(verifyPassword && comparePasword) {
            user.setPassword(encryptionService.encryptPassword(changePasswordRequest.getNewPassword()));
            iUserRepository.save(user);
        }
        return user;
    }


    @Override
    public Optional<Users> findByUserNameAndEmailIgnoreCase(String username, String email) {
        return iUserRepository.findByUserNameAndEmailIgnoreCase(username, email);
    }

    @Override
    public String resetPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Optional<Users> u = iUserRepository.findByUserNameIgnoreCase(forgotPasswordRequest.getUserName());
        if(u.isPresent()) {
            Users user = u.get();
            if(forgotPasswordRequest.getPassword().equals(forgotPasswordRequest.getConfirmPassword())) {
                user.setPassword(encryptionService.encryptPassword(forgotPasswordRequest.getPassword()));
                iUserRepository.save(user);
                return "Reset password successfully";
            } else throw new AppException(ErrorCode.CONFIRMED_PASSWORD_ERROR);
        } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }



    @Override
    public Users registerUser(RequestRegistrationUser requestRegistrationUser){
        if(iUserRepository.findByUserNameIgnoreCase(requestRegistrationUser.getUserName()).isPresent()
                && iUserRepository.findByEmailIgnoreCase(requestRegistrationUser.getEmail()).isPresent() ){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Users newUser = new Users();
        newUser.setUserName(requestRegistrationUser.getUserName());
        newUser.setEmail(requestRegistrationUser.getEmail());
        newUser.setPassword(encryptionService.encryptPassword(requestRegistrationUser.getPassword()));
        newUser.setPhone(requestRegistrationUser.getPhone());
        iUserRepository.save(newUser);
        return newUser;
    }

    @Override
    public void deleteUser(int id) {
        Users user = iUserRepository.findById(id);
        user.setStatus(false);
        iUserRepository.save(user);
    }

    @Override
    public Users updateUsers(int id, UpdateUserRequest update) {
        Users u = iUserRepository.findById(id);
        u.setName(update.getName());
        u.setEmail(update.getEmail());
        u.setPhone(update.getPhone());
        u.setAddress(update.getAddress());
        iUserRepository.save(u);
        return u;
    }

    @Override
    public List<Users> getAllUsers() {
        return iUserRepository.findAll();
    }

    @Override
    public Users getUser(String username, String password) {
        return iUserRepository.getUsersByUserNameAndPassword(username, password);
    }

    public String processOAuthPostLogin(String email, String name) {
        Optional<Users> u = iUserRepository.findByUserNameIgnoreCase(email);
        if(!u.isPresent()) {
            Users user = new Users();
            user.setUserName(email);
            user.setPassword("");
            user.setEmail(email);
            user.setRole(Role.Customer);
            user.setName(name);
            user.setStatus(true);
            iUserRepository.save(user);
            return jwtService.generateJWT(user);
        }
        return jwtService.generateJWT(u.get());
    }

    //For manager
    @Override
    public UserResponseDTO getAllAccount() {
        List<Users> customers = iUserRepository.findByRole(Role.Customer);
        List<Users> staff = iUserRepository.findByRole(Role.Staff);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setCustomers(customers);
        userResponseDTO.setStaff(staff);
        return userResponseDTO;
    }
    @Override
    public Users createUser(Users user) {
        if (user.getRole() == null || !(user.getRole() == Role.Customer || user.getRole() == Role.Staff)) {
            throw new IllegalArgumentException("Invalid role");
        }
        return iUserRepository.save(user);
    }

    @Override
    public Users updateUserForManager(int userId, Users user) {
        Users userUpdate = iUserRepository.findById(userId);
        if(userUpdate==null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }else{
            userUpdate.setUserName(user.getUserName());
            userUpdate.setPassword(user.getPassword());
            userUpdate.setName(user.getName());
            userUpdate.setEmail(user.getEmail());
            userUpdate.setPhone(user.getPhone());
            userUpdate.setAddress(user.getAddress());
            userUpdate.setRole(user.getRole());
            userUpdate.setStatus(user.isStatus());
            return iUserRepository.save(userUpdate);
        }
    }

}

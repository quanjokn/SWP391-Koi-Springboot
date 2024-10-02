package SWP391.Fall24.service;

import SWP391.Fall24.dto.request.LoginRequest;
import SWP391.Fall24.dto.request.RequestRegistrationUser;
import SWP391.Fall24.dto.request.UpdateUserRequest;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
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
        }
        return null;
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
        iUserRepository.deleteById(id);
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


}

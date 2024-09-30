package SWP391.Fall24.service;


import SWP391.Fall24.dto.request.LoginRequest;
import SWP391.Fall24.dto.request.RequestRegistrationUser;
import SWP391.Fall24.pojo.Users;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    public Users registerUser(RequestRegistrationUser requestRegistrationUser);

    public void deleteUser(int id);

//    public Users updateUsers(int id, Users user);

    public List<Users> getAllUsers();

    public Users getUser(String username, String password);

    public Optional<Users> findByID(int id);

    public String loginUser(LoginRequest loginRequest) ;

}

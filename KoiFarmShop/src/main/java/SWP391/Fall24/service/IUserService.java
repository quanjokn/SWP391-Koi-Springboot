package SWP391.Fall24.service;


import SWP391.Fall24.pojo.Users;

import java.util.List;

public interface IUserService {

    public Users saveUsers(Users user);

    public void deleteUser(int id);

    Users updateUsers(int id, Users user);

    public List<Users> getAllUsers();

    Users getUser(String username, String password);
}

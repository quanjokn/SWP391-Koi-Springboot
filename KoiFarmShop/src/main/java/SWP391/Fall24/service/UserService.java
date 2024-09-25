package SWP391.Fall24.service;

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

    @Override
    public Optional<Users> findByID(int id) {
        return iUserRepository.findById(id);
    }

    @Override
    public Users saveUsers(Users user) {
        return iUserRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        iUserRepository.deleteById(id);
    }

    @Override
    public Users updateUsers(int id, Users user) {
        for(Users u: iUserRepository.findAll()){
            if(u.getId() == id){
                u.setName(user.getName());
                u.setPassword(user.getPassword());
                u.setEmail(user.getEmail());
                u.setPhone(user.getPhone());
                u.setAddress(user.getAddress());
            }
        }
        return iUserRepository.getById(id);
    }

    @Override
    public List<Users> getAllUsers() {
        return iUserRepository.findAll();
    }

    @Override
    public Users getUser(String username, String password) {
        for(Users u1 : iUserRepository.findAll()){
            if(u1.getUserName().equals(username) && u1.getPassword().equals(password)){
                return u1;
            }
        }
        return null;
    }

}

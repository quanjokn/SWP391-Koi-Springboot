package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{

    @Autowired
    IUserRepository iUserRepository;

    @Override
    public Users saveUsers(Users user) {
        return iUserRepository.save(user);
    }


}

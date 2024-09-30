package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserCRUD extends CrudRepository<Users, Integer> {
    Users findByUserNameIgnoreCase(String userName);
}

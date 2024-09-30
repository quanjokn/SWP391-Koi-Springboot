package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories(basePackageClasses = Users.class)
public interface IUserRepository extends JpaRepository<Users, Integer> {

    Users findById(int id);

    Optional<Users> findByUserNameIgnoreCase(String username);

    Optional<Users> findByEmailIgnoreCase(String email);

//    void updateUsers(Users users);

    Users getUsersByUserNameAndPassword(String username, String password);
}

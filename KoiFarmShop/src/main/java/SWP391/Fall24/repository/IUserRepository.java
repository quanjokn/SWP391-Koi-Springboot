package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<Users, Integer> {

}

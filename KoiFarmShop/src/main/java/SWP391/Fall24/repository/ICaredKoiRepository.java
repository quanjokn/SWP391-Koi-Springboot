package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.CaredKois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICaredKoiRepository extends JpaRepository<CaredKois, Integer> {
}

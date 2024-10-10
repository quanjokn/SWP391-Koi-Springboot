package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Kois;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IKoiRepository extends JpaRepository <Kois , Integer> {
    Optional<Kois> findByFishId(int fishId);
}

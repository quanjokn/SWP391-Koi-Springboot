package SWP391.Fall24.service;

import SWP391.Fall24.dto.FishDetail;
import SWP391.Fall24.pojo.Fishes;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IFishService  {
    public List<FishDetail> allFish();

}

package SWP391.Fall24.service;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.pojo.Fishes;

import java.util.List;

public interface IFishService  {

    public List<FishDetailDTO> allFish();

    Fishes addFish(ConsignedKoiDTO fish);
}

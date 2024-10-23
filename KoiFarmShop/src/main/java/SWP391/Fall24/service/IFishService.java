package SWP391.Fall24.service;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.dto.Manager.AllFishDTO;
import SWP391.Fall24.dto.Top4FishDTO;
import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.pojo.Promotions;

import java.util.List;

public interface IFishService  {

    public List<FishDetailDTO> allFish();
  
    public Fishes findFishById(int fishId);

    Fishes addFish(ConsignedKoiDTO fish);

    AllFishDTO getAllFishForManager();

    public void addFishForManager(ConsignedKoiDTO consignedKoiDTO);

    public void deleteFish(int fishId);

    public Object searhFish(int fishId);

    public void updateFish(int fishId ,ConsignedKoiDTO consignedKoiDTO);

    public List<Top4FishDTO> getTop4Fish();
    //promotion
    public List<Promotions> getAllPromotions();
}

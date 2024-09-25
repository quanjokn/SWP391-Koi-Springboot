package SWP391.Fall24.service;

import SWP391.Fall24.dto.FishDetail;
import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.repository.IFishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class FishService implements IFishService {
    @Autowired
    private IFishRepository fishRepository;

    @Override
    public List<FishDetail> allFish() {
        List<FishDetail> listAllFish = new LinkedList<>();
        listAllFish.addAll(fishRepository.koiList());
        listAllFish.addAll(fishRepository.batchList());
        listAllFish.addAll(fishRepository.consignedKoiList());
        return fishRepository.koiList();
    }

//    @Override
//    public List<FishDetail> batchList() {
//        return fishRepository.BatchList();
//    }
//
//    @Override
//    public List<FishDetail> consignedKoiList() {
//        return fishRepository.ConsignedKoiList();
//    }

}

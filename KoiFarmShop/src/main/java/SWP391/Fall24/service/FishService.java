package SWP391.Fall24.service;

import SWP391.Fall24.dto.FishDetailDTO;
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
    public List<FishDetailDTO> allFish() {
        List<FishDetailDTO> listAllFish = new LinkedList<>();
        listAllFish.addAll(fishRepository.koiList());
        listAllFish.addAll(fishRepository.batchList());
        listAllFish.addAll(fishRepository.consignedKoiList());
        return listAllFish;
    }
    public Optional<FishDetailDTO> findFishDetailByFishId(int fishId) {
        List<FishDetailDTO> allFishDetails = allFish();  // Lấy toàn bộ danh sách FishDetailDTO
        return allFishDetails.stream()
                .filter(fishDetail -> fishDetail.getId() == fishId)
                .findFirst();
    }
}

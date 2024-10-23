package SWP391.Fall24.controller;

import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.dto.Manager.ProductSalesDTO;
import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.pojo.Kois;
import SWP391.Fall24.repository.IEvaluationRepository;
import SWP391.Fall24.repository.IFishRepository;
import SWP391.Fall24.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fish")
@CrossOrigin(origins = "http://localhost:3000")
public class FishController {
    @Autowired
    private FishService fishService;

    @Autowired
    private IEvaluationRepository evaluationRepository;
  
    @GetMapping("/fishes-list")
    private List<FishDetailDTO> getAllFish() {
        return fishService.allFish();
    }

    @PostMapping("/fish-detail/{id}")
    @ResponseBody
    private Optional<FishDetailDTO> getFish(@PathVariable("id") int id) {
        List<FishDetailDTO> allFish = fishService.allFish();
        for(FishDetailDTO fd : allFish) {
            if(fd.getId() == id) {
                Optional<FishDetailDTO> fishDetailDTO = Optional.of(fd);
                if(fishDetailDTO.isPresent()) {
                    FishDetailDTO fish = fishDetailDTO.get();
                    fish.setEvaluation(evaluationRepository.getEvaluationsByFishId(fish.getId()));
                }
                return fishDetailDTO;
            }
        }
        return Optional.empty();
    }

    @GetMapping("/top4Fish")
    private List<ProductSalesDTO> getTop4Fish() {
        return fishService.getTop4Fish();
    }
}

package SWP391.Fall24.controller;

import SWP391.Fall24.dto.FishDetailDTO;
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
                return Optional.of(fd);
            }
        }
        return Optional.empty();
    }



}

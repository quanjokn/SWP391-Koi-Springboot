package SWP391.Fall24.controller;


import SWP391.Fall24.dto.FishDetail;
import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fish")
@CrossOrigin
public class FishController {
    @Autowired
    private FishService fishService;

    @GetMapping("/getAllFishes")
    private List<FishDetail> getAllFish() {
        return fishService.allFish();
    }

    @PostMapping("/getFish/{id}")
    @ResponseBody
    private Optional<FishDetail> getFish(@PathVariable("id") int id) {
        List<FishDetail> allFish = fishService.allFish();
        for(FishDetail fd : allFish) {
            if(fd.getId() == id) {
                return Optional.of(fd);
            }
        }
        return Optional.empty();
    }



}

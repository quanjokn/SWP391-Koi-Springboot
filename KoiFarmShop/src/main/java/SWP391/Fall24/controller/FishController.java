package SWP391.Fall24.controller;


import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.service.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fish")
public class FishController {
    @Autowired
    private FishService fishService;

//    @GetMapping("/getAllFish")
//    private List<Fishes> getAllFish() {
//        return fishService.findAll();
//    }

//    @PostMapping("/getFish/{id}")
//    public ResponseEntity<Fishes> getAllFish(@PathVariable int id) {
//        return ResponseEntity.ok(fishService.getAllFishes(id));
//    }

    @PostMapping("/getFish/{id}")
    @ResponseBody
    private Optional<Fishes> getFish(@PathVariable("id") int id) {
        return fishService.findById(id);
    }



}

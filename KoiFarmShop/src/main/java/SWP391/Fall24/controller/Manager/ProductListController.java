package SWP391.Fall24.controller.Manager;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.dto.Manager.AllFishDTO;
import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.service.FishService;
import SWP391.Fall24.service.IFishService;
import org.apache.catalina.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/productList")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductListController {

    @Autowired
    private FishService fishService;

    @GetMapping("/allFish")
    public AllFishDTO allFishForManager() {
        return fishService.getAllFishForManager();
    }

    @PostMapping("/addFish")
    public void addFish(@RequestBody ConsignedKoiDTO consignedKoiDTO) {
        fishService.addFishForManager(consignedKoiDTO);
    }

    @PostMapping("/deleteFish/{fishId}")
    public void deleteFish(@PathVariable("fishId") int fishId) {
        fishService.deleteFish(fishId);
    }

    @PostMapping("/updateFish/{fishId}")
    public void updateFish(@PathVariable int fishId, @RequestBody ConsignedKoiDTO consignedKoiDTO) {
        fishService.updateFish(fishId,consignedKoiDTO);
    }

    @PostMapping("/searchFish/{fishId}")
    public Object searchFish(@PathVariable("fishId") int fishId) {
        return fishService.searhFish(fishId);
    }
 }

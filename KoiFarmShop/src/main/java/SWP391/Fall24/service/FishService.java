package SWP391.Fall24.service;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.pojo.Promotions;
import SWP391.Fall24.pojo.Species;
import SWP391.Fall24.repository.IFishRepository;
import SWP391.Fall24.repository.IOriginRepository;
import SWP391.Fall24.repository.ISpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FishService implements IFishService {
    @Autowired
    private IFishRepository fishRepository;

    @Autowired
    private IOriginRepository originRepository;

    @Autowired
    private ISpeciesRepository speciesRepository;

    @Override
    public List<FishDetailDTO> allFish() {
        List<FishDetailDTO> listAllFish = new LinkedList<>();
        listAllFish.addAll(fishRepository.koiList());
        listAllFish.addAll(fishRepository.batchList());
        listAllFish.addAll(fishRepository.consignedKoiList());
        return listAllFish;
    }

    @Override
    public Fishes findFishById(int fishId) {
        Optional<Fishes> fish = fishRepository.findById(fishId);
        return fish.isPresent() ? fish.get() :null;
    }
  
    @Override
    public Fishes addFish(ConsignedKoiDTO addF) {
        Fishes newF = new Fishes();
        newF.setCategory(addF.getCategory());
        newF.setOrigin(originRepository.findById(addF.getOrigin()));
        List<Species> speciesList = speciesRepository.findAll();
        // check species availability and add new species if needed
        Set<String> species = addF.getSpecies();
        Set<Species> species_fish = new HashSet<>();
        for(String s: species) {
            boolean check = false;
            for(Species sl : speciesList){
                if(sl.getName().toLowerCase().equals(s.toLowerCase())){
                    species_fish.add(sl);
                    check = true;
                    break;
                }
            }
            if(!check){
                Species newSpecies = speciesRepository.save(new Species(s));
                species_fish.add(newSpecies);
            }
        }
        newF.setSpecies(species_fish);
        return fishRepository.save(newF);
    }

    public Optional<FishDetailDTO> findFishDetailByFishId(int fishId) {
        List<FishDetailDTO> allFishDetails = this.allFish(); 
        return allFishDetails.stream()
                .filter(fishDetail -> fishDetail.getId() == fishId)
                .findFirst();
    }
}

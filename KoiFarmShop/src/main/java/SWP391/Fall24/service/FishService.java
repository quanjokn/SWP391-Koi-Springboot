package SWP391.Fall24.service;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.dto.Manager.AllFishDTO;
import SWP391.Fall24.dto.Manager.ProductSalesDTO;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.ConsignedKoiStatus;
import SWP391.Fall24.repository.*;
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

    @Autowired
    private IConsignedKoiRepository consignedKoiRepository;

    @Autowired
    private ICaredKoiRepository caredKoiRepository;

    @Autowired
    private IKoiRepository koiRepository;

    @Autowired
    private IBatchRepository batchRepository;

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private IPromotionRepository promotionRepository;



    @Override
    public List<FishDetailDTO> allFish() {
        List<FishDetailDTO> listAllFish = new LinkedList<>();
        listAllFish.addAll(fishRepository.koiList());
        listAllFish.addAll(fishRepository.batchList());
        listAllFish.addAll(fishRepository.consignedKoiList(ConsignedKoiStatus.Accepted_Selling.toString()));
        return listAllFish;
    }
    @Override
    public AllFishDTO getAllFishForManager() {
        AllFishDTO allFishDTO = new AllFishDTO();
        List<FishDetailDTO> listAllFish = new LinkedList<>();
        listAllFish.addAll(fishRepository.koiList());
        listAllFish.addAll(fishRepository.batchList());
        List<ConsignedKois> consignedKoisList = consignedKoiRepository.findAll();
        List<CaredKois> caredKoiList = caredKoiRepository.findAll();
        allFishDTO.setFishDetailDTO(listAllFish);
        allFishDTO.setConsignedKois(consignedKoisList);
        allFishDTO.setCaredKois(caredKoiList);
        return allFishDTO;
    }

    @Override
    public void addFishForManager(ConsignedKoiDTO consignedKoiDTO){
        Fishes fishes = addFish(consignedKoiDTO);
        if(consignedKoiDTO.getCategory().equals("Koi")){
            Kois kois = new Kois();
            kois.setAge(consignedKoiDTO.getAge());
            kois.setCertificate(consignedKoiDTO.getCertificate());
            kois.setCharacter(consignedKoiDTO.getCharacter());
            kois.setDescription(consignedKoiDTO.getDescription());
            kois.setHealthStatus(consignedKoiDTO.getHealthStatus());
            kois.setName(consignedKoiDTO.getName());
            kois.setPhoto(consignedKoiDTO.getPhoto());
            kois.setPrice(consignedKoiDTO.getPrice());
            kois.setQuantity(consignedKoiDTO.getQuantity());
            kois.setRation(consignedKoiDTO.getRation());
            kois.setSex(consignedKoiDTO.getSex());
            kois.setSize(consignedKoiDTO.getSize());
            kois.setStatus(kois.isStatus());
            kois.setVideo(consignedKoiDTO.getVideo());
            kois.setFish(fishRepository.findById(fishes.getId()).get());
            koiRepository.save(kois);
        }else if(consignedKoiDTO.getCategory().equals("Batch")){
            Batches batches = new Batches();
            batches.setAge(consignedKoiDTO.getAge());
            batches.setFish(fishRepository.findById(fishes.getId()).get());
            batches.setName(consignedKoiDTO.getName());
            batches.setDescription(consignedKoiDTO.getDescription());
            batches.setSex(consignedKoiDTO.getSex());
            batches.setCharacter(consignedKoiDTO.getCharacter());
            batches.setSize(consignedKoiDTO.getSize());
            batches.setPrice(consignedKoiDTO.getPrice());
            batches.setHealthStatus(consignedKoiDTO.getHealthStatus());
            batches.setRation(consignedKoiDTO.getRation());
            batches.setPhoto(consignedKoiDTO.getPhoto());
            batches.setVideo(consignedKoiDTO.getVideo());
            batches.setStatus(batches.isStatus());
            batchRepository.save(batches);
        }

    }

    @Override
    public void deleteFish(int fishId){
        Fishes fishes = findFishById(fishId);
        if(fishes.getCategory().equals("Koi")){
            Optional<Kois> opKois = koiRepository.findById(fishes.getId());
            if(opKois.isPresent()){
                koiRepository.delete(opKois.get());
                fishes.setOrigin(null);
                fishRepository.delete(fishes);
            }
        } else if(fishes.getCategory().equals("Batch")){
            Optional<Batches> batches = batchRepository.findByFishId(fishId);
            if(batches.isPresent()){
                batchRepository.delete(batches.get());
                fishes.setOrigin(null);
                fishRepository.delete(fishes);
            }
        }
    }

    @Override
    public void updateFish( int fishId ,ConsignedKoiDTO consignedKoiDTO){
        if(consignedKoiDTO.getCategory().equals("Koi")){
            Optional<Kois> opKois = koiRepository.findByFishId(fishId);
            Kois kois = opKois.get();
            kois.setFish(fishRepository.findById(fishId).get());
            kois.setName(consignedKoiDTO.getName());
            kois.setQuantity(consignedKoiDTO.getQuantity());
            kois.setDescription(consignedKoiDTO.getDescription());
            kois.setSex(consignedKoiDTO.getSex());
            kois.setAge(consignedKoiDTO.getAge());
            kois.setCertificate(consignedKoiDTO.getCertificate());
            kois.setSize(consignedKoiDTO.getSize());
            kois.setPrice(consignedKoiDTO.getPrice());
            kois.setHealthStatus(consignedKoiDTO.getHealthStatus());
            kois.setRation(consignedKoiDTO.getRation());
            kois.setPhoto(consignedKoiDTO.getPhoto());
            kois.setVideo(consignedKoiDTO.getVideo());
            kois.setCertificate(consignedKoiDTO.getCertificate());
            kois.setStatus(kois.isStatus());
            koiRepository.save(kois);
        } else if(consignedKoiDTO.getCategory().equals("Batch")){
            Optional<Batches> opBatches = batchRepository.findByFishId(fishId);
            Batches batches = opBatches.get();
            batches.setAge(consignedKoiDTO.getAge());
            batches.setFish(fishRepository.findById(fishId).get());
            batches.setName(consignedKoiDTO.getName());
            batches.setDescription(consignedKoiDTO.getDescription());
            batches.setSex(consignedKoiDTO.getSex());
            batches.setCharacter(consignedKoiDTO.getCharacter());
            batches.setSize(consignedKoiDTO.getSize());
            batches.setPrice(consignedKoiDTO.getPrice());
            batches.setHealthStatus(consignedKoiDTO.getHealthStatus());
            batches.setRation(consignedKoiDTO.getRation());
            batches.setPhoto(consignedKoiDTO.getPhoto());
            batches.setVideo(consignedKoiDTO.getVideo());
            batches.setStatus(batches.isStatus());
            batchRepository.save(batches);
        }
    }



    @Override
    public Object  searhFish(int fishId){
        AllFishDTO allFishDTO = getAllFishForManager();
        List<FishDetailDTO> listAllFish = allFishDTO.getFishDetailDTO();

        for (FishDetailDTO fish : listAllFish) {
            if (fish.getId() == fishId) {
                return fish;
            }
        }

        List<ConsignedKois> consignedKoisList = allFishDTO.getConsignedKois();
        for (ConsignedKois consignedFish : consignedKoisList) {
            if (consignedFish.getId() == fishId) {
                return consignedFish;
            }
        }


        return null;
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

    //top4 fish
    @Override
    public List<ProductSalesDTO> getTop4Fish() {
        List<Object[]> data = orderDetailRepository.findTop4FishByQuantity();
        List<ProductSalesDTO> productSalesDTOList = new ArrayList<>();
        for(Object[] row : data){
            int fishId = (int) row[0];
            int totalQuantity = (int) row[1];

            List<FishDetailDTO> fishDetailDTOList = this.allFish();
            String fishName="";
            for(FishDetailDTO f: fishDetailDTOList){
                if(f.getId()==fishId){
                    fishName = f.getName();
                }
            }
            productSalesDTOList.add(new ProductSalesDTO(fishName,totalQuantity));

        }
        return productSalesDTOList;
    }

    //promotion
    @Override
    public List<Promotions> getAllPromotions() {
        return promotionRepository.findAll();
    }

}

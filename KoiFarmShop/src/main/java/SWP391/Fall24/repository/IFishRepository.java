package SWP391.Fall24.repository;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import SWP391.Fall24.dto.FishDetailDTO;
import SWP391.Fall24.pojo.Fishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFishRepository extends JpaRepository<Fishes, Integer> {

    // 3 method to get selling fish in our shop
    @Query("select new SWP391.Fall24.dto.FishDetailDTO(f.id, k.name, k.quantity, k.description, k.sex, k.age, k.character, k.size, k.price, k.healthStatus, k.ration, k.photo, k.video, k.certificate, f.category, 0, 0, o.origin, f.rating ) " +
            "from Fishes f inner join Kois k on f.id = k.fish.id join Origins o on f.origin.id = o.id ")
    public List<FishDetailDTO> koiList();

    @Query("select new SWP391.Fall24.dto.FishDetailDTO(f.id, k.name, 1, k.description, k.sex, k.age, k.character, k.size, k.price, k.healthStatus, k.ration, k.photo, k.video, '', f.category, 0, 0, o.origin ,f.rating) " +
            "from Fishes f inner join Batches k on f.id = k.fish.id join Origins o on f.origin.id = o.id ")
    public List<FishDetailDTO> batchList();

    @Query("select new SWP391.Fall24.dto.FishDetailDTO(f.id, k.name, k.quantity, k.description, k.sex, k.age, k.character, k.size, k.price, k.healthStatus, k.ration, k.photo, k.video, k.certificate, f.category, 0, 0, o.origin ,f.rating) " +
            "from Fishes f inner join ConsignedKois k on f.id = k.fish.id join Origins o on f.origin.id = o.id where k.status = :status")
    public List<FishDetailDTO> consignedKoiList(@Param("status") String status);

    // get all consign order including fish
    @Query("select new SWP391.Fall24.dto.ConsignedKoiDTO(f.id, k.name, k.description, k.quantity, k.sex, k.age, k.character, k.price, k.size, k.healthStatus, k.ration, k.photo, k.video, k.certificate, f.category, 0, 0, f.origin.id, k.status) " +
            "from Fishes f inner join ConsignedKois k on f.id = k.fish.id join Origins o on f.origin.id = o.id where k.consignOrder.id = :serviceID")
    public List<ConsignedKoiDTO> allConsignedKoi(@Param("serviceID") int serviceID);
}

package SWP391.Fall24.repository;

import SWP391.Fall24.dto.FishDetail;
import SWP391.Fall24.pojo.Fishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFishRepository extends JpaRepository<Fishes, Integer> {

    @Query("select new SWP391.Fall24.dto.FishDetail(f.id, k.name, k.quantity, k.description, k.sex, k.age, k.character, k.size, k.price, k.healthStatus, k.ration, k.photo, k.video, k.certificate, f.category, 0, 0, o.origin) " +
            "from Fishes f inner join Kois k on f.id = k.fish.id join Origins o on f.origin.id = o.id")
    public List<FishDetail> koiList();

    @Query("select new SWP391.Fall24.dto.FishDetail(f.id, k.name, 1, k.description, k.sex, k.age, k.character, k.size, k.price, k.healthStatus, k.ration, k.photo, k.video, '', f.category, 0, 0, o.origin) " +
            "from Fishes f inner join Batches k on f.id = k.fish.id join Origins o on f.origin.id = o.id")
    public List<FishDetail> batchList();

    @Query("select new SWP391.Fall24.dto.FishDetail(f.id, k.name, 1, k.description, k.sex, k.age, k.character, k.size, k.price, k.healthStatus, k.ration, k.photo, k.video, k.certificate, f.category, 0, 0, o.origin) " +
            "from Fishes f inner join ConsignedKois k on f.id = k.fish.id join Origins o on f.origin.id = o.id")
    public List<FishDetail> consignedKoiList();
}

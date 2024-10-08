package SWP391.Fall24.service;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import SWP391.Fall24.dto.request.ConsignOrderRequest;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.ConsignOrders;
import SWP391.Fall24.pojo.ConsignedKois;
import SWP391.Fall24.pojo.Fishes;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ConsignOrderService implements IConsignOrderService {
    @Autowired
    private IConsignOrderRepository iConsignOrderRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private FishService fishService;

    @Autowired
    private IConsignedKoiRepository iConsignedKoiRepository;

    @Override
    public String createOrder(ConsignOrderRequest consignOrderRequest, int userId) {
        Optional<Users> u = iUserRepository.findUsersById(userId);
        if (u.isPresent()) {
            Users user = u.get();
            ConsignOrders consignOrders = new ConsignOrders();
            consignOrders.setUser(user);
            LocalDate localDate = LocalDate.now();
            consignOrders.setDate(localDate);
            consignOrders.setCommission(consignOrderRequest.getCommission());
            consignOrders.setTotalPrice(consignOrderRequest.getTotalPrice());
            ConsignOrders order = iConsignOrderRepository.save(consignOrders);

            // Create Fishes to get id
            List<ConsignedKoiDTO> consignedKoiDTOS = consignOrderRequest.getConsignList();
            for(ConsignedKoiDTO koi : consignedKoiDTOS) {
                Fishes newF = fishService.addFish(koi);
                ConsignedKois consignedKoi = new ConsignedKois();
                consignedKoi.setFish(newF);
                consignedKoi.setId(newF.getId());
                consignedKoi.setName(koi.getName());
                consignedKoi.setDescription(koi.getDescription());
                consignedKoi.setSex(koi.getSex());
                consignedKoi.setAge(koi.getAge());
                consignedKoi.setCharacter(koi.getCharacter());
                consignedKoi.setSize(koi.getSize());
                consignedKoi.setPrice(koi.getPrice());
                consignedKoi.setHealthStatus(koi.getHealthStatus());
                consignedKoi.setRation(koi.getRation());
                consignedKoi.setPhoto(koi.getPhoto());
                consignedKoi.setVideo(koi.getVideo());
                consignedKoi.setCertificate(koi.getCertificate());
                consignedKoi.setCustomerID(userId);
                consignedKoi.setConsignOrder(order);
                iConsignedKoiRepository.save(consignedKoi);
            }
        } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return "Create order successfully";
    }
}

package SWP391.Fall24.service;

import SWP391.Fall24.dto.ConsignedKoiDTO;
import SWP391.Fall24.dto.request.ConsignApprovalRequest;
import SWP391.Fall24.dto.request.ConsignOrderRequest;
import SWP391.Fall24.dto.response.ConsignOrderResponse;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.ConsignOrderStatus;
import SWP391.Fall24.pojo.Enum.ConsignedKoiStatus;
import SWP391.Fall24.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    private IFishRepository iFishRepository;

    @Autowired
    private ISpeciesRepository speciesRepository;

    @Override
    public ConsignOrderResponse createOrder(ConsignOrderRequest consignOrderRequest, int userId) {
        ConsignOrderResponse consignOrderResponse = new ConsignOrderResponse();
        Optional<Users> u = iUserRepository.findUsersById(userId);
        if (u.isPresent()) {
            Users user = u.get();
            consignOrderResponse.setCustomer(user);
            consignOrderResponse.setStatus(ConsignedKoiStatus.Pending_confirmation.toString());
            consignOrderResponse.setRequest(consignOrderRequest);

            ConsignOrders consignOrders = new ConsignOrders();
            consignOrders.setUser(user);
            LocalDate localDate = LocalDate.now();
            consignOrders.setDate(localDate);
            consignOrders.setCommission(consignOrderRequest.getCommission());
            consignOrders.setTotalPrice(consignOrderRequest.getTotalPrice());
            ConsignOrders order = iConsignOrderRepository.save(consignOrders);

            consignOrderResponse.setOrderID(order.getId());

            // Create Fishes to get id
            List<ConsignedKoiDTO> consignedKoiDTOS = consignOrderRequest.getConsignList();
            for(ConsignedKoiDTO koi : consignedKoiDTOS) {
                int i = 0;
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
                consignedKoi.setStatus(ConsignedKoiStatus.Pending_confirmation.toString());

                consignOrderResponse.getRequest().getConsignList().get(i).setStatus(ConsignedKoiStatus.Pending_confirmation.toString());

                iConsignedKoiRepository.save(consignedKoi);
            }
        } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return consignOrderResponse;
    }

    @Override
    public ConsignOrderResponse getDetail(int orderID) {
        ConsignOrders order = iConsignOrderRepository.findById(orderID).orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        ConsignOrderResponse consignOrderResponse = new ConsignOrderResponse();
        // set data into consignOrderRequest
        consignOrderResponse.setOrderID(orderID);
        consignOrderResponse.setCustomer(order.getUser());
        consignOrderResponse.setStatus(order.getStatus());

        ConsignOrderRequest orderRequest = new ConsignOrderRequest();
        orderRequest.setDate(order.getDate());
        orderRequest.setCommission(order.getCommission());
        orderRequest.setTotalPrice(order.getTotalPrice());

        List<ConsignedKoiDTO> consignDTO = iFishRepository.allConsignedKoi(orderID);

        consignDTO.forEach(response->{
            Set<String> setSpecies = new HashSet<>();
            List<Species> speciesNames = speciesRepository.getListName(response.getFishID());
            speciesNames.forEach(s -> setSpecies.add(s.getName()));
            response.setSpecies(setSpecies);
        });

        orderRequest.setConsignList(consignDTO);
        consignOrderResponse.setRequest(orderRequest);
        return consignOrderResponse;
    }

}

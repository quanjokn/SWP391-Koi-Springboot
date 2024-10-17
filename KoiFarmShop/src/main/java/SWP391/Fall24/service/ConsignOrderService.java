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
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ConsignOrderService implements IConsignOrderService {
    @Autowired
    private IConsignOrderRepository iConsignOrderRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private FishService fishService;

    @Autowired
    private IFishRepository iFishRepository;

    @Autowired
    private ISpeciesRepository speciesRepository;

    @Autowired
    private IConsignedKoiRepository iConsignedKoiRepository;

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
            // set species
            Set<String> setSpecies = new HashSet<>();
            List<Species> speciesNames = speciesRepository.getListName(response.getFishID());
            speciesNames.forEach(s -> setSpecies.add(s.getName()));
            response.setSpecies(setSpecies);
            // set status
            ConsignedKois koi = iConsignedKoiRepository.findConsignedKoisById(response.getFishID()).orElseThrow(()-> new AppException(ErrorCode.FISH_NOT_EXISTED));
            koi.setStatus(response.getStatus());
        });

        orderRequest.setConsignList(consignDTO);
        consignOrderResponse.setRequest(orderRequest);
        return consignOrderResponse;
    }

    @Override
    public String receiveConsignOrder(int orderID, int staffID){
        Users staff = iUserRepository.findUsersById(staffID).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        ConsignOrders order = iConsignOrderRepository.findById(orderID).orElseThrow(()-> new AppException(ErrorCode.ORDER_NOT_EXISTED));
        order.setStaff(staff);
        order.setStatus(ConsignOrderStatus.Receiving.toString());
        iConsignOrderRepository.save(order);
        return "Consign order "+orderID+" received by "+staff.getName();
    }


    @Override
    public String approvalResponse(ConsignApprovalRequest consignApprovalRequest, int staffID) {
        Optional<ConsignOrders> consignOrders = iConsignOrderRepository.findById(consignApprovalRequest.getOrderID());
        Optional<Users> opStaff = iUserRepository.findUsersById(staffID);
        if(consignOrders.isPresent()) {
            ConsignOrders consignOrder = consignOrders.get();
            if(opStaff.isPresent()) {
                HashMap<Integer, Boolean> approval = consignApprovalRequest.getDecision();
                AtomicInteger i = new AtomicInteger();
                approval.values().forEach(value->{
                    if(value) i.getAndIncrement();
                });
                if(i.get()>0){
                    consignOrder.setStatus(ConsignOrderStatus.Responded.toString());
                } else consignOrder.setStatus(ConsignOrderStatus.Rejected.toString());
                consignOrder.setNote(consignApprovalRequest.getNote());
                iConsignOrderRepository.save(consignOrder);
                // set consignFish status
                ConsignOrderResponse consignOrderResponse = this.getDetail(consignApprovalRequest.getOrderID());
                HashMap<Integer, Boolean> decision = consignApprovalRequest.getDecision();
                decision.forEach((fishID, result)->{
                    ConsignedKois koi = iConsignedKoiRepository.findConsignedKoisById(fishID).orElseThrow(()->new AppException(ErrorCode.FISH_NOT_EXISTED));
                    if(result) koi.setStatus(ConsignedKoiStatus.Accepted_Selling.toString());
                    else koi.setStatus(ConsignedKoiStatus.Rejected.toString());
                    iConsignedKoiRepository.save(koi);
                });
            } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
        } else throw new AppException(ErrorCode.ORDER_NOT_EXISTED);
        return "Responded consign order "+consignApprovalRequest.getOrderID()+" by "+opStaff.get().getName();
    }

    @Override
    public List<ConsignOrders> findByStatus(String status) {
        List<ConsignOrders> list = iConsignOrderRepository.findAll();
        List<ConsignOrders> consignOrders = new ArrayList<>();
        list.forEach(order->{
            if(order.getStatus().equals(status)) consignOrders.add(order);
        });
        return consignOrders;
    }

    @Override
    public List<ConsignOrders> getReceivingOrder(int staffID){
        List<ConsignOrders> consignOrders = new ArrayList<>();
        this.findByStatus(ConsignOrderStatus.Receiving.toString()).forEach(order->{
            if(order.getStaff().getId() == staffID) consignOrders.add(order);
        });
        return consignOrders;
    }



//    public String completeOrder(int staffID, int orderID){
//        ConsignOrders consignOrders = iConsignOrderRepository.findById(orderID);
//        if(consignOrders.getStaff().getId()==staffID){
//            consignOrders.setStatus(CaringOrderStatus.Done.toString());
//            iConsignOrderRepository.save(caringOrder);
//            List<ConsignedKois> consignedKois = consignedKoiRepository.findByConsignOrder(consignOrders);
//            caredKois.forEach(koi->{
//                if(koi.getStatus().equals(ConsignedKoiStatus.Accepted_caring.toString())){
//                    koi.setStatus(CaredKoiStatus.Done.toString());
//                    caredKoiRepository.save(koi);
//                }
//            });
//        } else throw new AppException(ErrorCode.OUT_OF_ROLE);
//        return "Complete caring order successfully";
//    }
}

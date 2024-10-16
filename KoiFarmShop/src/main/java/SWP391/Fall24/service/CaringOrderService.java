package SWP391.Fall24.service;

import SWP391.Fall24.dto.CaredKoiDTO;
import SWP391.Fall24.dto.request.CareApprovalRequest;
import SWP391.Fall24.dto.request.CaringOrderRequest;
import SWP391.Fall24.dto.response.CaringOrderResponse;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.CaredKois;
import SWP391.Fall24.pojo.CaringOrders;
import SWP391.Fall24.pojo.Enum.CaredKoiStatus;
import SWP391.Fall24.pojo.Enum.CaringOrderStatus;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.ICaredKoiRepository;
import SWP391.Fall24.repository.ICaringOrderRepository;
import SWP391.Fall24.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CaringOrderService implements ICaringOrderService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICaringOrderRepository caringOrderRepository;

    @Autowired
    private ICaredKoiRepository caredKoiRepository;


    @Override
    public String createCaringOrder(CaringOrderRequest request, int userId) {
        Optional<Users> u = userRepository.findUsersById(userId);
        if(u.isPresent()) {
            Users user = u.get();
            // create caring order in CaringOrders to take the id
            CaringOrders order = new CaringOrders();
            order.setStartDate(request.getStartDate());
            order.setEndDate(request.getEndDate());
            order.setTotalPrice(request.getTotalPrice());
            order.setCustomer(user);
            LocalDate today = LocalDate.now();
            order.setDate(today);
            CaringOrders caringOrder = caringOrderRepository.save(order);
            // create cared Koi in CaredKoi
            List<CaredKoiDTO> listKoi = request.getCaredKoiList();
            for(CaredKoiDTO koi : listKoi) {
                // insert value into CaredKoi
                CaredKois ck = new CaredKois();
                ck.setCaringOrder(caringOrder);
                ck.setName(koi.getName());
                ck.setSex(koi.getSex());
                ck.setAge(koi.getAge());
                ck.setSize(koi.getSize());
                ck.setHealthStatus(koi.getHealthStatus());
                ck.setRation(koi.getRation());
                ck.setPhoto("/images/"+koi.getPhoto());
                ck.setStatus(CaredKoiStatus.Pending_confirmation.toString());
                // save to CaredKois table
                caredKoiRepository.save(ck);
            }
            return "Create caring order successfully";
        } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }

    @Override
    public CaringOrderResponse getDetail(int orderID) {
        CaringOrderResponse caringOrderResponse = new CaringOrderResponse();
        CaringOrders caringOrder = caringOrderRepository.findById(orderID);
        List<CaredKois> caredKois = caredKoiRepository.findByCaringOrderId(orderID);
        caringOrderResponse.setCaringOrder(caringOrder);
        caringOrderResponse.setCaredKois(caredKois);
        return caringOrderResponse;
    }

    @Override
    public String approvalCaringOrder(CareApprovalRequest approvalRequest){
        CaringOrders caringOrder = caringOrderRepository.findById(approvalRequest.getOrderID());
        if(caringOrder.getStaff().getId()==approvalRequest.getStaffID()){
            HashMap<Integer, Boolean> approval = approvalRequest.getDecision();
            AtomicInteger i = new AtomicInteger();
            approval.values().forEach(value->{
                if(value) i.getAndIncrement();
            });
            if(i.get()>0){
                caringOrder.setStatus(CaringOrderStatus.Responded.toString());
            } else caringOrder.setStatus(CaringOrderStatus.Rejected.toString());
            caringOrder.setNote(approvalRequest.getNote());
            caringOrderRepository.save(caringOrder);
            List<CaredKois> caredKois = caredKoiRepository.findByCaringOrder(caringOrder);
            caredKois.forEach(koi->{
                approval.forEach((fishID, decision)->{
                    if(fishID==koi.getId()){
                        if(decision){
                            koi.setStatus(CaredKoiStatus.Accepted_caring.toString());
                        } else koi.setStatus(CaredKoiStatus.Rejected.toString());
                        caredKoiRepository.save(koi);
                    }
                });
            });
        } else throw new AppException(ErrorCode.OUT_OF_ROLE);
        return "Approval caring order successfully";
    }

    @Override
    public List<CaringOrders> getCaringOrdersByStatus(String status) {
        List<CaringOrders> result = new ArrayList<>();
        List<CaringOrders> all = caringOrderRepository.findAll();
        all.forEach(order->{
            if(order.getStatus().equals(status)) {
                result.add(order);
            }
        });
        return result;
    }

    @Override
    public String receivingCaringOrder(int staffID, int orderID) {
        String result = "";
        Optional<Users> staff = userRepository.findUsersById(staffID);
        CaringOrders order = caringOrderRepository.findById(orderID);
        if(staff.isPresent()) {
            Users staffUser = staff.get();
            order.setStaff(staffUser);
            order.setStatus(CaringOrderStatus.Receiving.toString());
            caringOrderRepository.save(order);
            result = "Caring order #"+orderID+" is received by "+staffUser.getUserName();
        } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return result;
    }

    @Override
    public List<CaringOrders> getReceivingOrder(int staffID) {
        List<CaringOrders> result = new ArrayList<>();
        this.getCaringOrdersByStatus(CaringOrderStatus.Receiving.toString()).forEach(order->{
            if(order.getStaff().getId()==staffID){
                result.add(order);
            }
        });
        return result;
    }

    public String completeOrder(int staffID, int orderID) {
        CaringOrders caringOrder = caringOrderRepository.findById(orderID);
        if(caringOrder.getStaff().getId()==staffID){
            caringOrder.setStatus(CaringOrderStatus.Done.toString());
            caringOrderRepository.save(caringOrder);
            List<CaredKois> caredKois = caredKoiRepository.findByCaringOrder(caringOrder);
            caredKois.forEach(koi->{
               if(koi.getStatus().equals(CaredKoiStatus.Accepted_caring.toString())){
                   koi.setStatus(CaredKoiStatus.Done.toString());
                   caredKoiRepository.save(koi);
               }
            });
        } else throw new AppException(ErrorCode.OUT_OF_ROLE);
        return "Complete caring order successfully";
    }
}

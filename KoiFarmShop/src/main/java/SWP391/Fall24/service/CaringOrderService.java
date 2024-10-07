package SWP391.Fall24.service;

import SWP391.Fall24.dto.CaredKoiDTO;
import SWP391.Fall24.dto.request.CaringOrderRequest;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.CaredKois;
import SWP391.Fall24.pojo.CaringOrders;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.ICaredKoiRepository;
import SWP391.Fall24.repository.ICaringOrderRepository;
import SWP391.Fall24.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                // save to CaredKois table
                caredKoiRepository.save(ck);
            }
            return "Create caring order successfully";
        } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
    }
}

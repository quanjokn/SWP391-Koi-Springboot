package SWP391.Fall24.service;

import SWP391.Fall24.dto.*;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static SWP391.Fall24.pojo.OrderStatus.Pending_confirmation;


@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository iOrderRepository;
    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;
    @Autowired
    private IFishRepository iFishRepository;

//    @Override
//    public Orders saveOrderOld(CartDTO cartDTO, Users user) {
//        Orders o = new Orders();
//        float total = 0;
//        for(CartItemDTO i: cartDTO.getCartItems()){
//            total += i.getUnitPrice() * i.getQuantity();
//        }
//        LocalDate date = LocalDate.now();
//
//        o.setTotal(total);
//        o.setDate(date);
//        o.setStatus(OrderStatus.valueOf("Pending_confirmation"));
//        o.setCustomer(user);
//        Orders savedOrder = iOrderRepository.save(o);
//
//        for(CartItemDTO c: cartDTO.getCartItems()){
//            Optional<Fishes> optionalFish = iFishRepository.findById(c.getFishId());
//            if(optionalFish.isPresent()){
//                Fishes fish = optionalFish.get();
//                OrderDetails od = new OrderDetails();
//
//                od.setOrders(savedOrder);
//                od.setFishes(fish);
//                od.setQuantity(c.getQuantity());
//                od.setPrice(c.getUnitPrice());
//                od.setTotal(c.getTotalPrice());
//                od.setDiscount(0);
//                od.setFeedback("fdsfa");
//                od.setRating(3);
//                iOrderDetailRepository.save(od);
//
//            } else
//                throw new AppException(ErrorCode.FISH_NOT_EXISTED);
//    }
//       return savedOrder;
//
//    }

    @Override
    public OrderDTO getOrderDetails(int orderId) {
        Optional<Orders> optionalOrder = iOrderRepository.findById(orderId);

        Orders order = optionalOrder.get();
        List<OrderDetails> orderDetails = iOrderDetailRepository.findByOrdersId(orderId);
        int totalQuantity = 0;
        float totalPrice = 0;
        for (OrderDetails od : orderDetails) {
            totalQuantity += od.getQuantity();
            totalPrice += od.getTotal();
        }
        List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();
        for (OrderDetails od : orderDetails) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(od.getFishes().getId(), od.getFishName(), od.getQuantity(), od.getPrice() ,od.getTotal());
            orderDetailsDTOList.add(orderDetailsDTO);
        }
        OrderDTO orderDTO  = new OrderDTO();
        orderDTO.setId(orderId);
        orderDTO.setDate(LocalDate.now());
        orderDTO.setTotalOrderPrice(totalPrice);
        orderDTO.setStatus(Pending_confirmation);
        orderDTO.setTotalQuantity(totalQuantity);
        orderDTO.setOrderDetailsDTO(orderDetailsDTOList);

        return orderDTO;
    }

    @Autowired
    private ICartItemRepository iCartItemRepository;

    @Override
    public void saveOrder(Cart cart, Users user) {
        Orders o = new Orders();
        LocalDate date = LocalDate.now();

        o.setTotal(cart.getTotalPrice());
        o.setDate(date);
        o.setStatus(OrderStatus.valueOf("Pending_confirmation"));
        o.setCustomer(user);
        Orders savedOrder = iOrderRepository.save(o);
        List<CartItem> listCartItems = iCartItemRepository.findByCardId(cart.getId());

        List<FishDetailDTO> fishDetailDTO = iFishRepository.batchList();
        fishDetailDTO.addAll(iFishRepository.koiList());
        fishDetailDTO.addAll(iFishRepository.consignedKoiList());

        for(CartItem c: listCartItems){
            OrderDetails od = new OrderDetails();
            od.setOrders(savedOrder);
            String fishName="";
            for(FishDetailDTO f: fishDetailDTO){
                if(f.getId()==c.getFish().getId()){
                    fishName = f.getName();
                }
            }
            od.setFishes(c.getFish());
            od.setFishName(fishName);
            od.setQuantity(c.getQuantity());
            od.setPrice(c.getUnitPrice());
            od.setTotal(c.getTotalPrice());
            iOrderDetailRepository.save(od);
        }
    }

}




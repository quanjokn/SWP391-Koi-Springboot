package SWP391.Fall24.service;

import SWP391.Fall24.dto.*;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.OrderStatus;
import SWP391.Fall24.repository.*;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static SWP391.Fall24.pojo.Enum.OrderStatus.Pending_confirmation;
import static SWP391.Fall24.pojo.Enum.Role.Staff;


@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository iOrderRepository;
    @Autowired
    private IOrderDetailRepository iOrderDetailRepository;

    @Autowired
    private ICartRepository iCartRepository;
    @Autowired
    private FishService fishService;
    @Autowired
    private ICartItemRepository iCartItemRepository;

    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IKoiRepository iKoiRepository;

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
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(od.getFishes().getId(), od.getFishName(), od.getQuantity(), od.getPrice() ,od.getTotal() ,od.getPhoto());
            orderDetailsDTOList.add(orderDetailsDTO);
        }
        OrderDTO orderDTO  = new OrderDTO();
        orderDTO.setOrderId(orderId);
        orderDTO.setUsers(order.getCustomer());
        orderDTO.setDate(LocalDate.now());
        orderDTO.setTotalOrderPrice(totalPrice);
        orderDTO.setStatus(OrderStatus.Pending_confirmation.toString());
        orderDTO.setTotalQuantity(totalQuantity);
        orderDTO.setOrderDetailsDTO(orderDetailsDTOList);

        return orderDTO;
    }



    @Override
    public int saveOrder(Cart cart, Users user , PlaceOrderDTO placeOrderDTO) {
        Orders o = new Orders();
        LocalDate date = LocalDate.now();

        o.setTotal(cart.getTotalPrice());
        o.setDate(date);
        o.setCustomer(user);
        o.setPayment(placeOrderDTO.getPaymentMethod());
        Orders savedOrder = iOrderRepository.save(o);
        List<CartItem> listCartItems = iCartItemRepository.findByCardId(cart.getId());
        List<FishDetailDTO> fishDetailDTOList = fishService.allFish();

        for(CartItem c: listCartItems){
            OrderDetails od = new OrderDetails();
            od.setOrders(savedOrder);
            String fishName="";
            String photo = "";
            for(FishDetailDTO f: fishDetailDTOList){
                if(f.getId()==c.getFish().getId()){
                    fishName = f.getName();
                    photo = f.getPhoto();
                }
            }
            od.setFishes(c.getFish());
            od.setFishName(fishName);
            od.setQuantity(c.getQuantity());
            od.setPrice(c.getUnitPrice());
            od.setTotal(c.getTotalPrice());
            od.setPhoto(photo);
            iOrderDetailRepository.save(od);

        }
        iCartRepository.deleteById(cart.getId());
        return o.getId();
    }

    @Override
    public List<Orders> getAllOrders() {
        List<Orders> listOrders = iOrderRepository.findAll();
        List<Orders> orders = new ArrayList<>();
        for (Orders order : listOrders) {
            if(order.getStatus().equals(OrderStatus.Pending_confirmation.toString())){
                orders.add(order);
            }
        }
        return orders;
    }
    public List<Orders> getStaffOrders(int staffId) {
        List<Orders> ordersList = iOrderRepository.findByStaffId(staffId);
        return ordersList;
    }


    public Orders receiveOrder(int orderId , int staffId){
        Optional<Orders> opOrder = iOrderRepository.findById(orderId);
        Optional<Users> opStaff = iUserRepository.findUsersById(staffId);
        Orders order = opOrder.get();
        order.setStatus(OrderStatus.Receiving.toString());
        order.setStaff(opStaff.get());
        iOrderRepository.save(order);
        return order;
    }

    public Orders prepareOrder(int orderId ) {

        Optional<Orders> opOrder = iOrderRepository.findById(orderId);
        Orders order = opOrder.get();
        order.setStatus(OrderStatus.Preparing.toString());


        List<OrderDetails> orderDetailsList = iOrderDetailRepository.findByOrdersId(orderId);
        for(OrderDetails od: orderDetailsList){
            int fishId = od.getFishes().getId();
            Optional<Kois> opKois = iKoiRepository.findByFishId(fishId);
            Kois kois = opKois.get();
            kois.setQuantity(kois.getQuantity()-od.getQuantity());
            iKoiRepository.save(kois);
        }
        return order;
    }

    public Orders acceptedOrder(int orderId) {
        Optional<Orders> opOrder = iOrderRepository.findById(orderId);
        Orders order = opOrder.get();
        order.setStatus(OrderStatus.Completed.toString());
        iOrderRepository.save(order);
        return order;
    }

    public Orders rejectOrder(int orderId ){
        Optional<Orders> opOrders = iOrderRepository.findById(orderId);
        Orders order = opOrders.get();
        order.setStatus(OrderStatus.Rejected.toString());

        iOrderRepository.save(order);
        return order;
    }

    public List<OrderDetails> getUserOrderDetails(int orderId) {
        List<OrderDetails> orderDetailsList = iOrderDetailRepository.findByOrdersId(orderId);
        return orderDetailsList;
    }


}




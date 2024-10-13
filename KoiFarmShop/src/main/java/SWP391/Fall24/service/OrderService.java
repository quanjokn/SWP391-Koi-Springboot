package SWP391.Fall24.service;

import SWP391.Fall24.dto.*;
import SWP391.Fall24.dto.Staff.AllOrderDTO;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.OrderStatus;
import SWP391.Fall24.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    @Autowired
    private IConsignOrderRepository iConsignOrderRepository;
    @Autowired
    private ICaringOrderRepository iCaringOrderRepository;

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
        orderDTO.setStatus(order.getStatus());
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
    public List<Orders> findOrderByUserId(int userId) {
        Optional<Users> users = iUserRepository.findUsersById(userId);
        if(!users.isPresent()) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        List<Orders> orders = iOrderRepository.findByCustomerId(userId);
        return orders;
    }
    @Override
    public List<OrderDetails> getOrderDetailsForStaff(int orderId) {
        List<OrderDetails> orderDetailsList = iOrderDetailRepository.findByOrdersId(orderId);
        return orderDetailsList;
    }

    @Override
    public List<Orders> getAllOrders() {
        List<Orders> listOrders = iOrderRepository.findAll();
        List<Orders> orders = new ArrayList<>();

        for (Orders order : listOrders) {
            if(order.getStaff()==null){
                orders.add(order);
            }
        }
        return orders;
    }

    @Override
    public List<Orders> getStaffOrders(int staffId) {
        List<Orders> ordersList = iOrderRepository.findByStaffId(staffId);
        return ordersList;
    }

    @Override
    public Orders receiveOrder(int orderId , int staffId){
        Optional<Orders> opOrder = iOrderRepository.findById(orderId);
        Optional<Users> opStaff = iUserRepository.findUsersById(staffId);
        Orders order = opOrder.get();
        order.setStaff(opStaff.get());
        iOrderRepository.save(order);
        return order;
    }

    @Override
    public Orders handleOrder(int orderId , OrderStatus status) {
        Optional<Orders> opOrder = iOrderRepository.findById(orderId);
        Orders order = opOrder.get();

        order.setStatus(status.toString());
        iOrderRepository.save(order);

        if(status.toString().equals(OrderStatus.Preparing.toString())){
            List<OrderDetails> orderDetailsList = iOrderDetailRepository.findByOrdersId(orderId);
            for(OrderDetails od: orderDetailsList){
                int fishId = od.getFishes().getId();
                Optional<Kois> opKois = iKoiRepository.findById(fishId);
                Kois koi = opKois.get();
                koi.setQuantity(koi.getQuantity()-od.getQuantity());
                iKoiRepository.save(koi);
            }
        }
        return order;
    }
    @Override
    public Orders rejectOrder(OrderManagementDTO orderManagementDTO){
        Optional<Orders> opOrder = iOrderRepository.findById(orderManagementDTO.getOrderId());
        Orders order = opOrder.get();
        order.setStatus(OrderStatus.Rejected.toString());
        order.setNote(orderManagementDTO.getNote());
        iOrderRepository.save(order);
        return order;
    }

    @Override
    public List<AllOrderDTO> getAllOrdersForStaff(int userId) {
        Optional<Users> users = iUserRepository.findUsersById(userId);
        List<Orders> orders = iOrderRepository.findByCustomerId(userId);
        List<ConsignOrders> consignOrders = iConsignOrderRepository.findAllByUser(users.get());
        List<CaringOrders> caringOrders = iCaringOrderRepository.findByCustomerId(userId);

        AllOrderDTO allOrderDTO = new AllOrderDTO();
        allOrderDTO.setOrder(orders);
        allOrderDTO.setConsignOrders(consignOrders);
        allOrderDTO.setCaringOrders(caringOrders);

        List<AllOrderDTO> allOrderDTOList = new ArrayList<>();
        allOrderDTOList.add(allOrderDTO);
        return allOrderDTOList;
    }


}




package SWP391.Fall24.service;

import SWP391.Fall24.dto.*;
import SWP391.Fall24.dto.Manager.OrdersRevenueDTO;
import SWP391.Fall24.dto.Manager.ProductSalesDTO;
import SWP391.Fall24.dto.Manager.WeekSalesDTO;
import SWP391.Fall24.dto.Staff.AllOrderDTO;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.ConsignedKoiStatus;
import SWP391.Fall24.pojo.Enum.OrderStatus;
import SWP391.Fall24.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
    private IFishRepository iFishRepository;

    @Autowired
    private ICartItemRepository iCartItemRepository;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IKoiRepository iKoiRepository;

    @Autowired
    private ConsignOrderService consignOrderService;

    @Autowired
    private CaringOrderService caringOrderService;

    @Autowired
    private IBatchRepository iBatchRepository;

    @Autowired
    private IConsignedKoiRepository iConsignedKoiRepository;

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
                Fishes fish = iFishRepository.findById(fishId).orElseThrow(()-> new AppException(ErrorCode.FISH_NOT_EXISTED));
                if(fish.getCategory().equals("Koi")) {
                    Kois koi = iKoiRepository.findById(fishId).orElseThrow(()-> new AppException(ErrorCode.KOI_NOT_EXISTED));
                    koi.setQuantity(koi.getQuantity() - od.getQuantity());
                    iKoiRepository.save(koi);
                } else if(fish.getCategory().equals("Batch")) {
                    Batches batches = iBatchRepository.findById(fishId).orElseThrow(()-> new AppException(ErrorCode.BATCH_NOT_EXISTED));
                    batches.setStatus(false);
                    iBatchRepository.save(batches);
                } else if(fish.getCategory().equals("ConsignedKoi")) {
                    ConsignedKois consignedKoi = iConsignedKoiRepository.findById(fishId).orElseThrow(()->new AppException(ErrorCode.CONSIGNED_KOI_NOT_EXISTED));
                    consignedKoi.setQuantity(consignedKoi.getQuantity() - od.getQuantity());
                    // check consignkoi out of stuck, so change status to Sold
                    if(consignedKoi.getQuantity()==0){
                        consignedKoi.setStatus(ConsignedKoiStatus.Sold.toString());
                    }
                    iConsignedKoiRepository.save(consignedKoi);
                } else throw new AppException(ErrorCode.OUT_OF_CATEGORY_FISH);
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
    public AllOrderDTO getAllOrdersForStaff(int staffId) {
        List<Orders> orders = this.getStaffOrders(staffId);
        List<ConsignOrders> consignOrders = consignOrderService.getReceivingOrder(staffId);
        List<CaringOrders> caringOrders = caringOrderService.getReceivingOrder(staffId);

        AllOrderDTO allOrderDTO = new AllOrderDTO();
        allOrderDTO.setOrder(orders);
        allOrderDTO.setConsignOrders(consignOrders);
        allOrderDTO.setCaringOrders(caringOrders);

        return allOrderDTO;
    }




    //for dashboard
    @Override
    public List<OrdersRevenueDTO> getOrdersRevenueForDashBoard(int year, int month) {
        List<Object[]> results = iOrderRepository.findOrdersAndRevenueByWeek(year, month);
        List<OrdersRevenueDTO> revenueDTOList = new ArrayList<>();
        for (Object[] result : results) {
            int weekOfMonth = (int) result[0];
            int totalOrders = (int) result[1];
            Double totalRevenue = (Double) result[2];


            OrdersRevenueDTO dto = new OrdersRevenueDTO(weekOfMonth, totalOrders, totalRevenue);
            revenueDTOList.add(dto);
        }
        return revenueDTOList;
    }

    @Override
    public List<WeekSalesDTO> getWeeklySales(int year, int month) {
        List<Object[]> data = iOrderRepository.findSalesByWeek(year, month);

        List<WeekSalesDTO> result = new ArrayList<>();
        List<ProductSalesDTO> productSalesDTOList = new ArrayList<>();
        int currentWeek = -1;

        for(Object[] row: data){
            int weekOfMonth = (int) row[0];
            int fishId = (int) row[1];
            int totalQuantity = (int) row[2];

            if (currentWeek != weekOfMonth && productSalesDTOList.size() > 0) {
                result.add(processWeekData(currentWeek, productSalesDTOList));
                productSalesDTOList.clear();
            }

            currentWeek = weekOfMonth;
            List<FishDetailDTO> fishDetailDTOList = fishService.allFish();
            String fishName="";
            for(FishDetailDTO f: fishDetailDTOList){
                if(f.getId()==fishId){
                    fishName = f.getName();
                }
            }
            productSalesDTOList.add(new ProductSalesDTO(fishName, totalQuantity));
        }
        if (productSalesDTOList.size() > 0) {
            result.add(processWeekData(currentWeek, productSalesDTOList));
        }

        return result;

    }

    private WeekSalesDTO processWeekData(int weekOfMonth, List<ProductSalesDTO> products) {
        products.sort(Comparator.comparing(ProductSalesDTO::getTotalQuantity).reversed());

        List<ProductSalesDTO> top3Products = new ArrayList<>();
        int totalOtherSales =0;

        for (int i = 0; i < products.size(); i++) {
            if (i < 3) {
                top3Products.add(products.get(i));
            } else {
                totalOtherSales += products.get(i).getTotalQuantity();
            }
        }
        if (totalOtherSales > 0) {
            top3Products.add(new ProductSalesDTO("Orther", totalOtherSales));
        }
        return new WeekSalesDTO(weekOfMonth, top3Products);
    }
}




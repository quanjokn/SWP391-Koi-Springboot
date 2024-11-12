package SWP391.Fall24.service;

import SWP391.Fall24.dto.*;
import SWP391.Fall24.dto.Manager.AllRevenueOfMonthDTO;
import SWP391.Fall24.dto.Manager.MonthSalesDTO;
import SWP391.Fall24.dto.Manager.OrderRevenueOfWeekDTO;
import SWP391.Fall24.dto.Manager.ProductSalesDTO;
import SWP391.Fall24.dto.Staff.AllOrderDTO;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.ConsignedKoiStatus;
import SWP391.Fall24.pojo.Enum.OrderStatus;
import SWP391.Fall24.repository.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;


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
    private IConsignOrderRepository consignOrderRepository;

    @Autowired
    private ICaringOrderRepository caringOrderRepository;

    @Autowired
    private IBatchRepository iBatchRepository;

    @Autowired
    private IConsignedKoiRepository iConsignedKoiRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ICaringOrderRepository carringOrderRepository;

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
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO(od.getFishes().getId(), od.getFishName(), od.getQuantity(), od.getPrice() ,od.getTotal() ,od.getPhoto(), od.getEvaluationStatus());
            orderDetailsDTOList.add(orderDetailsDTO);
        }
        OrderDTO orderDTO  = new OrderDTO();
        orderDTO.setOrderId(orderId);
        orderDTO.setUsers(order.getCustomer());
        orderDTO.setDate(LocalDate.now());
        orderDTO.setTotalOrderPrice(totalPrice);
        orderDTO.setStatus(order.getStatus());
        orderDTO.setNote(order.getNote());
        orderDTO.setTotalQuantity(totalQuantity);
        orderDTO.setOrderDetailsDTO(orderDetailsDTOList);

        return orderDTO;
    }



    @Override
    public int saveOrder(Cart cart, Users user , PlaceOrderDTO placeOrderDTO) throws MessagingException {
        Optional<Users> opUsers = iUserRepository.findUsersById(user.getId());
        Users users = opUsers.get();
        Orders o = new Orders();
        LocalDate date = LocalDate.now();

        if(users.getPoint()>200){
            o.setTotal((float) (cart.getTotalPrice()-(cart.getTotalPrice())*0.1));
        }else{
            o.setTotal(cart.getTotalPrice());
        }

        o.setDate(date);
        o.setCustomer(user);
        o.setPayment(placeOrderDTO.getPaymentMethod());
        List<CartItem> listCartItems = iCartItemRepository.findByCardId(cart.getId());

        List<FishDetailDTO> fishDetailDTOList = fishService.allFish();
        for(CartItem ci: listCartItems) {
            for (FishDetailDTO f : fishDetailDTOList) {
                if (f.getId() == ci.getFish().getId()) {
                    if (ci.getQuantity() > f.getQuantity()) {
                        throw new AppException(ErrorCode.QUANTITY_EXCESS);
                    }
                }
            }
        }
        for(CartItem c: listCartItems){
            OrderDetails od = new OrderDetails();
            od.setOrders(o);
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
            od.setPrice(c.getUnitPrice());
            od.setQuantity(c.getQuantity());
            if(users.getPoint()>200){
                od.setTotal((float) (c.getTotalPrice()-(c.getTotalPrice()*0.1)));
                od.setDiscount(0.1F);
            }else{
                od.setTotal(c.getTotalPrice());
            }
            od.setPhoto(photo);
                iOrderDetailRepository.save(od);
        }
        iCartRepository.deleteById(cart.getId());

        //user point
        float orderPrice = cart.getTotalPrice();
        int promotion = (int) (orderPrice/100000);//1 diem = 100k

        users.setPoint(users.addPoint(promotion));
        iUserRepository.save(users);
        // send thank-you email
        emailService.sendMail(user.getEmail(), emailService.subjectOrder(user.getName()), emailService.messageOrder(date, o.getTotal(), user.getAddress(), "Đợi Xác Nhận"));

        iOrderRepository.save(o);
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
    public Orders handleOrder(int orderId , OrderStatus status) throws MessagingException {
        Optional<Orders> opOrder = iOrderRepository.findById(orderId);
        Orders order = opOrder.get();

        order.setStatus(status.toString());
        iOrderRepository.save(order);
        // send mail
        String statusOrder = "";
        if(status.toString().equals("Completed")) statusOrder="Đã Hoàn Thành";
        else if(status.toString().equals("Shipping")) statusOrder= "Đang Vận Chuyển";
        else if(status.toString().equals("Preparing")) statusOrder = "Đang Chuẩn Bị";
        LocalDate date = LocalDate.now();
        emailService.sendMail(order.getCustomer().getEmail(), emailService.subjectOrder(order.getCustomer().getName()), emailService.messageOrder(date, order.getTotal(), order.getCustomer().getAddress(), statusOrder));

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
                    Users consignedKoiOwner = iUserRepository.findUsersById(consignedKoi.getCustomerID()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
                    emailService.sendMail(consignedKoiOwner.getEmail(), emailService.subjectOrder(consignedKoiOwner.getName()), emailService.messageConsignedKoiSold(consignedKoi.getConsignOrder(), consignedKoi));
                    iConsignedKoiRepository.save(consignedKoi);
                } else throw new AppException(ErrorCode.OUT_OF_CATEGORY_FISH);
            }
        }
        return order;
    }
    @Override
    public Orders rejectOrder(OrderManagementDTO orderManagementDTO) throws MessagingException {
        Optional<Orders> opOrder = iOrderRepository.findById(orderManagementDTO.getOrderId());
        Orders order = opOrder.get();
        order.setStatus(OrderStatus.Rejected.toString());
        order.setNote(orderManagementDTO.getNote());
        //email
        LocalDate date = LocalDate.now();
        emailService.sendMail(order.getCustomer().getEmail(), emailService.subjectOrder(order.getCustomer().getName()), emailService.messageDecline(orderManagementDTO.getNote(), order.getId(), order.getCustomer().getName()));
        iOrderRepository.save(order);
        return order;
    }

    @Override
    public AllOrderDTO getAllOrdersForStaff(int staffId) {
        List<Orders> orders = iOrderRepository.findByStaffId(staffId);
        List<ConsignOrders> consignOrders = consignOrderRepository.findByStaffId(staffId);
        List<CaringOrders> caringOrders = caringOrderRepository.findByStaffId(staffId);

        AllOrderDTO allOrderDTO = new AllOrderDTO();
        allOrderDTO.setOrder(orders);
        allOrderDTO.setConsignOrders(consignOrders);
        allOrderDTO.setCaringOrders(caringOrders);

        return allOrderDTO;
    }


    //for dashboard
    @Override
    public AllRevenueOfMonthDTO getOrdersRevenueForDashBoard(int year, int month) {
        List<Object[]> results = iOrderRepository.findOrdersAndRevenueByWeek(year, month);
        List<OrderRevenueOfWeekDTO> revenueDTOList = new ArrayList<>();
        AllRevenueOfMonthDTO allOrderRevenueDTO = null;
        for (Object[] result : results) {
            int weekOfMonth = (int) result[0];
            int totalOrders = (int) result[1];
            Double totalOrderPrice = (Double) result[2];

            Double totalConsign = consignOrderRepository.findTotalForConsignOrders(year, month, weekOfMonth);
            if (totalConsign == null) {
                totalConsign = 0.0;
            }
            Double totalCaring = caringOrderRepository.findTotalForCaringOrder(year, month, weekOfMonth);
            if (totalCaring == null) {
                totalCaring = 0.0;
            }
            Double totalRevenueOfWeek = totalOrderPrice - (totalConsign * 0.9) + totalCaring;
            OrderRevenueOfWeekDTO dto = new OrderRevenueOfWeekDTO(weekOfMonth, totalOrders, totalRevenueOfWeek);
            revenueDTOList.add(dto);
            Double allOrder = iOrderRepository.findAllOrderRevenue(year, month);
            if(allOrder ==null){
                allOrder = 0.0;
            }
            Double allConsignOrder = consignOrderRepository.findAllConsignOrders(year, month); // comission
            if(allConsignOrder ==null){
                allConsignOrder = 0.0;
            }
            Double allCaringOrder = caringOrderRepository.findAllCaringOrder(year, month);
            if(allCaringOrder ==null){
                allCaringOrder = 0.0;
            }
            double allRevenue = allOrder - (allConsignOrder*0.9) + allCaringOrder;

            allOrderRevenueDTO = new AllRevenueOfMonthDTO(allRevenue, revenueDTOList);
        }

        return allOrderRevenueDTO;
    }

    @Override
    public List<MonthSalesDTO> getMonthlySales(int year, int month) {
        List<Object[]> data = iOrderRepository.findSalesByMonth(year, month);
        List<ProductSalesDTO> productSalesDTOList = new ArrayList<>();

        for (Object[] row : data) {
            int fishId = (int) row[0];
            int totalQuantity = (int) row[1];

            List<FishDetailDTO> fishDetailDTOList = fishService.allFish();
            String fishName = "";
            for (FishDetailDTO f : fishDetailDTOList) {
                if (f.getId() == fishId) {
                    fishName = f.getName();
                    break;
                }
            }

            productSalesDTOList.add(new ProductSalesDTO(fishName, totalQuantity));
        }

        List<ProductSalesDTO> top3Products = processMonthlyData(productSalesDTOList);

        return Collections.singletonList(new MonthSalesDTO(month, top3Products));
    }

    private List<ProductSalesDTO> processMonthlyData(List<ProductSalesDTO> products) {
        products.sort(Comparator.comparing(ProductSalesDTO::getTotalQuantity).reversed());

        List<ProductSalesDTO> top3Products = new ArrayList<>();
        int totalOtherSales = 0;

        for (int i = 0; i < products.size(); i++) {
            if (i < 3) {
                top3Products.add(products.get(i));
            } else {
                totalOtherSales += products.get(i).getTotalQuantity();
            }
        }
        if (totalOtherSales > 0) {
            top3Products.add(new ProductSalesDTO("Others", totalOtherSales));
        }
        return top3Products;
    }


}




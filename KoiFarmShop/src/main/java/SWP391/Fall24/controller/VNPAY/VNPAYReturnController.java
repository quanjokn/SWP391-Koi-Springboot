package SWP391.Fall24.controller.VNPAY;

import SWP391.Fall24.dto.PlaceOrderDTO;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.pojo.Enum.CaredKoiStatus;
import SWP391.Fall24.pojo.Enum.CaringOrderStatus;
import SWP391.Fall24.pojo.Enum.ConsignOrderStatus;
import SWP391.Fall24.repository.*;
import SWP391.Fall24.service.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/api/payment/return")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class VNPAYReturnController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ICartRepository icartRepository;

    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private IOrderInvoiceVNPayRepository invoiceRepository;

    @Autowired
    private IConsignOrderInvoiceVNPay consignOrderInvoiceVNPay;

    @Autowired
    private IConsignOrderRepository consignOrderRepository;

    @Autowired
    private ConsignOrderVNPayService consignOrderVNPayService;

    @Autowired
    private CaringOrderVNPayService caringOrderVNPayService;

    @Autowired
    private ICaringOrderInvoiceVNPayRepository caringOrderInvoiceVNPayRepository;

    @Autowired
    private ICaringOrderRepository icaringOrderRepository;

    @Autowired
    private ICaredKoiRepository iCaredKoiRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String vnpayReturn(HttpServletRequest request, Model model) throws MessagingException {
        Map<String, String[]> params = request.getParameterMap();
        long vnp_Amount = Long.parseLong(Arrays.stream(params.get("vnp_Amount")).findFirst().get())/100;
        long vnpInvoiceCode = Long.parseLong(Arrays.stream(params.get("vnp_TxnRef")).findFirst().get());
        String vnp_OrderInfo = Arrays.stream(params.get("vnp_OrderInfo")).findFirst().get();

        if(vnp_OrderInfo.contains("Thanh toan don hang")){
            OrderInvoiceVNPay invoices = invoiceService.findByVnp_InvoiceCodeAndAndVnpAmount(vnpInvoiceCode, vnp_Amount);
            log.info(invoices.getUser().toString());
            int orderID = 0;
            if(Arrays.stream(params.get("vnp_TransactionStatus")).findFirst().get().equals("00")){
                Optional<Users> u = userService.findByID(invoices.getUser().getId());
                if(u.isPresent()) {
                    Users user = u.get();
                    Optional<Cart> opCart = icartRepository.findByUserId(invoices.getUser().getId());
                    if(opCart.isPresent()) {
                        Cart cart = opCart.get();
                        PlaceOrderDTO placeOrderDTO = new PlaceOrderDTO(invoices.getUser().getId(), "VNPay");
                        orderID = orderService.saveOrder(cart, user , placeOrderDTO);
                    } else throw new AppException(ErrorCode.CART_NULL);
                } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
            invoices.setVnpResponseCode(Arrays.stream(params.get("vnp_TransactionStatus")).findFirst().get());
            if(Arrays.stream(params.get("vnp_TransactionStatus")).findFirst().get().equals("00")) invoices.setStatus("Thành công");
            else invoices.setStatus("Thất bại");
            invoices.setOrders(orderRepository.findById(orderID).orElseThrow(()->new AppException(ErrorCode.ORDER_NOT_EXISTED)));
            invoices.setVnpTransactionNo(Long.parseLong(Arrays.stream(params.get("vnp_TransactionNo")).findFirst().get()));
            invoiceRepository.save(invoices);
            model.addAttribute("PAYMENT_RESULT", invoices);
        } else if(vnp_OrderInfo.contains("Thanh toan ki gui ban")){
            ConsignOrderInvoiceVNPay invoices = consignOrderVNPayService.findByVnp_invoice_code(vnpInvoiceCode);
            if(Arrays.stream(params.get("vnp_TransactionStatus")).findFirst().get().equals("00")){
                Optional<Users> u = userService.findByID(invoices.getUser().getId());
                if(u.isPresent()) {
                    ConsignOrders consignOrders = consignOrderRepository.findById(invoices.getOrders().getId()).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
                    consignOrders.setStatus(ConsignOrderStatus.Shared.toString());
                    consignOrders.getConsignDateStatus().setPaymentDate(LocalDate.now());
                    consignOrderRepository.save(consignOrders);
                    // send email after having pay commission

                    emailService.sendMail(u.get().getEmail(), emailService.subjectOrder(u.get().getName()), emailService.messageConsignedKoiShared(consignOrders));
                }
                invoices.setVnpResponseCode(Arrays.stream(params.get("vnp_TransactionStatus")).findFirst().get());
                invoices.setStatus("Thành công");
            } else{
                invoices.setStatus("Thất bại");
            }
            invoices.setVnpTransactionNo(Long.parseLong(Arrays.stream(params.get("vnp_TransactionNo")).findFirst().get()));
            consignOrderInvoiceVNPay.save(invoices);
            model.addAttribute("PAYMENT_RESULT", invoices);
        } else if(vnp_OrderInfo.contains("Thanh toan ki gui cham soc")){
            CaringOrderInvoiceVNPay invoices = caringOrderVNPayService.findByVnp_invoice_code(vnpInvoiceCode);
            if(Arrays.stream(params.get("vnp_TransactionStatus")).findFirst().get().equals("00")){
                Optional<Users> u = userService.findByID(invoices.getUser().getId());
                if(u.isPresent()) {
                    CaringOrders caringOrder = icaringOrderRepository.findById(invoices.getOrders().getId());
                    caringOrder.setStatus(CaringOrderStatus.Paid.toString());
                    caringOrder.getCareDateStatus().setPaymentDate(LocalDate.now());
                    icaringOrderRepository.save(caringOrder);
                    List<CaredKois> caredKoiList = iCaredKoiRepository.findByCaringOrder(caringOrder);
                    caredKoiList.forEach(koi->{
                        if(koi.getStatus().equals(CaredKoiStatus.Pending_payment.toString())){
                            koi.setStatus(CaredKoiStatus.Accepted_caring.toString());
                            iCaredKoiRepository.save(koi);
                        }
                    });
                }
                invoices.setVnpResponseCode(Arrays.stream(params.get("vnp_TransactionStatus")).findFirst().get());
                invoices.setStatus("Thành công");
            } else{
                invoices.setStatus("Thất bại");
            }
            invoices.setVnpTransactionNo(Long.parseLong(Arrays.stream(params.get("vnp_TransactionNo")).findFirst().get()));
            caringOrderInvoiceVNPayRepository.save(invoices);
            model.addAttribute("PAYMENT_RESULT", invoices);
        }
        return "returnVnpay";
    }
}

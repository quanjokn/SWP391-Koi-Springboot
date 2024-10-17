package SWP391.Fall24.controller.VNPAY;

import SWP391.Fall24.dto.PlaceOrderDTO;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.Cart;
import SWP391.Fall24.pojo.Invoices;
import SWP391.Fall24.pojo.Users;
import SWP391.Fall24.repository.ICartRepository;
import SWP391.Fall24.repository.IInvoiceRepository;
import SWP391.Fall24.repository.IOrderRepository;
import SWP391.Fall24.service.InvoiceService;
import SWP391.Fall24.service.OrderService;
import SWP391.Fall24.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

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
    private IInvoiceRepository invoiceRepository;

    @GetMapping
    public String vnpayReturn(HttpServletRequest request, Model model) {
        Map<String, String[]> params = request.getParameterMap();
        long vnp_Amount = Long.parseLong(Arrays.stream(params.get("vnp_Amount")).findFirst().get())/100;
        long vnpInvoiceCode = Long.parseLong(Arrays.stream(params.get("vnp_OrderInfo")).findFirst().get().split(":")[1]);
        Invoices invoices = invoiceService.findByVnp_InvoiceCodeAndAndVnpAmount(vnpInvoiceCode, vnp_Amount);
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
        return "returnVnpay";
    }
}

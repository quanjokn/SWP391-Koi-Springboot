package SWP391.Fall24.controller.VNPAY;

import SWP391.Fall24.config.VNPAYConfig;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class VNPayController {

    private VNPAYConfig Config;

    @Autowired
    private UserService userService;

    @Autowired
    private ICartRepository icartRepository;

    @Autowired
    private IInvoiceRepository invoiceRepository;

    @PostMapping("/create_payment/{userID}")
    public String createPayment(HttpServletRequest request, @PathVariable("userID") int userID) throws IOException {
        Optional<Users> u = userService.findByID(userID);
        Users customer = new Users();
        if(u.isPresent()) {
            customer = u.get();
        } else throw new AppException(ErrorCode.USER_NOT_EXISTED);
        Optional<Cart> opCart = icartRepository.findByUserId(userID);
        Cart cart = new Cart();
        if(opCart.isPresent()) {
            cart = opCart.get();
        } else throw new AppException(ErrorCode.CART_NULL);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = cart.getTotalPrice().longValue();

        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // Nhân với 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));

        // invoices
        Invoices invoices = new Invoices();
        invoices.setUser(customer);
        invoices.setVnp_InvoiceCode(Long.parseLong(vnp_TxnRef));
        invoices.setStatus("Đang giao dịch");
        invoices.setVnpAmount(amount);
        invoiceRepository.save(invoices);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = Config.vnp_PayUrl + "?" + query.toString();
        return paymentUrl;
    }
}

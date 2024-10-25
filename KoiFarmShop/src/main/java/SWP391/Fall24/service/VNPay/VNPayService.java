package SWP391.Fall24.service.VNPay;

import SWP391.Fall24.config.VNPAYConfig;
import SWP391.Fall24.exception.AppException;
import SWP391.Fall24.exception.ErrorCode;
import SWP391.Fall24.pojo.*;
import SWP391.Fall24.repository.*;
import SWP391.Fall24.service.ICaringOrderService;
import SWP391.Fall24.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayService implements IVNPayService {
    private VNPAYConfig Config;

    @Autowired
    private UserService userService;

    @Autowired
    private ICartRepository icartRepository;

    @Autowired
    private IOrderInvoiceVNPayRepository invoiceRepository;

    @Autowired
    private IConsignOrderRepository consignOrderRepository;

    @Autowired
    private IConsignOrderInvoiceVNPay consignOrderInvoiceVNPay;

    @Autowired
    private ICaringOrderInvoiceVNPayRepository caringOrderInvoiceVNPayRepository;

    @Autowired
    private ICaringOrderRepository caringOrderRepository;



    public String vnpayOrder(HttpServletRequest request, String type, int userID, int orderId, int vnpayCode, String content) throws IOException {

        // find customer
        Optional<Users> u = userService.findByID(userID);
        Users customer = new Users();
        if(u.isPresent()) {
            customer = u.get();
        } else throw new AppException(ErrorCode.USER_NOT_EXISTED);

        long amount = 0;

        if(type.equals("order")){
            Optional<Cart> opCart = icartRepository.findByUserId(userID);
            Cart cart = new Cart();
            if(opCart.isPresent()) {
                cart = opCart.get();
            } else throw new AppException(ErrorCode.CART_NULL);
            amount = cart.getTotalPrice().longValue();
            // invoice
            OrderInvoiceVNPay invoices = new OrderInvoiceVNPay();
            invoices.setUser(customer);
            invoices.setVnp_InvoiceCode(vnpayCode);
            invoices.setStatus("Đang giao dịch");
            invoices.setVnpAmount(amount);
            invoiceRepository.save(invoices);
        } else if(type.equals("consignOrder")){
            ConsignOrders consignOrders = consignOrderRepository.findById(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));
            amount =(long) consignOrders.getTotalPrice();
            //invoice
            ConsignOrderInvoiceVNPay invoice = new ConsignOrderInvoiceVNPay();
            invoice.setUser(customer);
            invoice.setOrders(consignOrders);
            invoice.setVnp_InvoiceCode(vnpayCode);
            invoice.setStatus("Đang giao dịch");
            invoice.setVnpAmount(amount);
            consignOrderInvoiceVNPay.save(invoice);
        } else if(type.equals("caringOrder")){
            CaringOrders caringOrder = caringOrderRepository.findById(orderId);
            amount = (long) caringOrder.getTotalPrice();
            CaringOrderInvoiceVNPay invoice = new CaringOrderInvoiceVNPay();
            invoice.setUser(customer);
            invoice.setOrders(caringOrder);
            invoice.setVnp_InvoiceCode(vnpayCode);
            invoice.setStatus("Đang giao dịch");
            invoice.setVnpAmount(amount);
            caringOrderInvoiceVNPayRepository.save(invoice);
        } else throw new AppException(ErrorCode.ORDER_NOT_EXISTED);

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // Nhân với 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", String.valueOf(vnpayCode));
        vnp_Params.put("vnp_OrderInfo", content);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", Config.getIpAddress(request));

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

package SWP391.Fall24.controller.VNPAY;

import SWP391.Fall24.service.VNPay.VNPayService;
import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class VNPayController {

    @Autowired
    private VNPayService vnPayService;

    @PostMapping("/create_payment/{type}/{userId}/{vnpayCode}/{content}")
    public String vnpayOrder(HttpServletRequest request, @PathVariable("type") String type, @PathVariable("userId") int userId, @PathVariable("vnpayCode") int vnpayCode, @PathVariable("content") String content) throws IOException {
        return vnPayService.vnpayOrder(request, type, userId, vnpayCode, content);
    }
}

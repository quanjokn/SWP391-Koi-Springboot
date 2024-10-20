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

    @PostMapping("/create_payment/{userID}")
    public String vnpayOrder(HttpServletRequest request, @PathVariable("userID") int userID) throws IOException {
        return vnPayService.vnpayOrder(request, userID);
    }
}

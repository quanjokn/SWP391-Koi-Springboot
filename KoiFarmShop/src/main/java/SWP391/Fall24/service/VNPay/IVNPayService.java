package SWP391.Fall24.service.VNPay;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

public interface IVNPayService {
    public String vnpayOrder(HttpServletRequest request, @PathVariable("userID") int userID) throws IOException;
}

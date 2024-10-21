package SWP391.Fall24.service;

import SWP391.Fall24.pojo.CaringOrderInvoiceVNPay;
import SWP391.Fall24.pojo.ConsignOrderInvoiceVNPay;
import SWP391.Fall24.repository.ICaringOrderInvoiceVNPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CaringOrderVNPayService implements ICaringOrderVNPayService {

    @Autowired
    private ICaringOrderInvoiceVNPayRepository caringOrderInvoiceVNPayRepository;

    @Override
    public CaringOrderInvoiceVNPay findByVnp_invoice_code(long vnp_invoice_code) {
        List<CaringOrderInvoiceVNPay> list = caringOrderInvoiceVNPayRepository.findAll();
        AtomicReference<CaringOrderInvoiceVNPay> caringOrderInvoiceVNPay = new AtomicReference<>();
        list.forEach(order->{
            if(order.getVnp_InvoiceCode() == vnp_invoice_code) caringOrderInvoiceVNPay.set(order);
        });
        return caringOrderInvoiceVNPay.get();
    }
}

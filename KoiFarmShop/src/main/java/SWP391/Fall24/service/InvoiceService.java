package SWP391.Fall24.service;

import SWP391.Fall24.pojo.OrderInvoiceVNPay;
import SWP391.Fall24.repository.IOrderInvoiceVNPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class InvoiceService implements IInvoiceService {
    @Autowired
    private IOrderInvoiceVNPayRepository invoiceRepository;

    @Override
    public OrderInvoiceVNPay findByVnp_InvoiceCodeAndAndVnpAmount(long vnp_InvoiceCode, long vnpAmount) {
        List<OrderInvoiceVNPay> list = invoiceRepository.findAll();
        AtomicReference<OrderInvoiceVNPay> result = new AtomicReference<>(new OrderInvoiceVNPay());
        list.forEach(invoice -> {
            if(invoice.getVnp_InvoiceCode()==vnp_InvoiceCode&&invoice.getVnpAmount()==vnpAmount) {
                result.set(invoice);
            }
        });
        return result.get();
    }
}

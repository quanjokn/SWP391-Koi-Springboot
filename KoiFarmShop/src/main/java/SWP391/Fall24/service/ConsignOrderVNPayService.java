package SWP391.Fall24.service;

import SWP391.Fall24.pojo.ConsignOrderInvoiceVNPay;
import SWP391.Fall24.repository.IConsignOrderInvoiceVNPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ConsignOrderVNPayService implements IConsignOrderVNPayService{

    @Autowired
    private IConsignOrderInvoiceVNPay invoiceVNPay;

    @Override
    public ConsignOrderInvoiceVNPay findByVnp_invoice_code(long vnp_invoice_code) {
        List<ConsignOrderInvoiceVNPay> list = invoiceVNPay.findAll();
        AtomicReference<ConsignOrderInvoiceVNPay> consignOrderInvoiceVNPay = new AtomicReference<>();
        list.forEach(order->{
            if(order.getVnp_InvoiceCode() == vnp_invoice_code) consignOrderInvoiceVNPay.set(order);
        });
        return consignOrderInvoiceVNPay.get();
    }
}

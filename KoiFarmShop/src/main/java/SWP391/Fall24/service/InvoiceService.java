package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Invoices;
import SWP391.Fall24.repository.IInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class InvoiceService implements IInvoiceService {
    @Autowired
    private IInvoiceRepository invoiceRepository;

    @Override
    public Invoices findByVnp_InvoiceCodeAndAndVnpAmount(long vnp_InvoiceCode, long vnpAmount) {
        List<Invoices> list = invoiceRepository.findAll();
        AtomicReference<Invoices> result = new AtomicReference<>(new Invoices());
        list.forEach(invoice -> {
            if(invoice.getVnp_InvoiceCode()==vnp_InvoiceCode&&invoice.getVnpAmount()==vnpAmount) {
                result.set(invoice);
            }
        });
        return result.get();
    }
}

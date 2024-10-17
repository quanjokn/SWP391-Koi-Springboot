package SWP391.Fall24.service;

import SWP391.Fall24.pojo.Invoices;

public interface IInvoiceService {
    Invoices findByVnp_InvoiceCodeAndAndVnpAmount(long vnp_InvoiceCode, long vnpAmount);
}

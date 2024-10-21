package SWP391.Fall24.service;

import SWP391.Fall24.pojo.OrderInvoiceVNPay;

public interface IInvoiceService {
    OrderInvoiceVNPay findByVnp_InvoiceCodeAndAndVnpAmount(long vnp_InvoiceCode, long vnpAmount);
}

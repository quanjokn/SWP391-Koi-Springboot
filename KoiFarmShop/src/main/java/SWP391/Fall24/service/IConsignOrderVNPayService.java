package SWP391.Fall24.service;

import SWP391.Fall24.pojo.ConsignOrderInvoiceVNPay;
import SWP391.Fall24.pojo.ConsignOrders;

public interface IConsignOrderVNPayService {
    public ConsignOrderInvoiceVNPay findByVnp_invoice_code(long vnp_invoice_code);
}

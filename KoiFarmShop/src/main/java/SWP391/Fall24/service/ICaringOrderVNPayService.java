package SWP391.Fall24.service;

import SWP391.Fall24.pojo.CaringOrderInvoiceVNPay;
import SWP391.Fall24.pojo.ConsignOrderInvoiceVNPay;

public interface ICaringOrderVNPayService {
    public CaringOrderInvoiceVNPay findByVnp_invoice_code(long vnp_invoice_code);
}

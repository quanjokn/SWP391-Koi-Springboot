package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.ConsignOrderInvoiceVNPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConsignOrderInvoiceVNPay extends JpaRepository<ConsignOrderInvoiceVNPay, Integer> {
}

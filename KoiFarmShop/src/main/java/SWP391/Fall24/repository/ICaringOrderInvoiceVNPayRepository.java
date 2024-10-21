package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.CaringOrderInvoiceVNPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICaringOrderInvoiceVNPayRepository extends JpaRepository<CaringOrderInvoiceVNPay, Integer> {
}

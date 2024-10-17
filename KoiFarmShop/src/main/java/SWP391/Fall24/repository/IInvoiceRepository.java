package SWP391.Fall24.repository;

import SWP391.Fall24.pojo.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoices, Integer> {

}

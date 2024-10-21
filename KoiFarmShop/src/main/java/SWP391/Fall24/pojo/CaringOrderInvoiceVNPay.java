package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "CaringOrderInvoiceVNPay")
public class CaringOrderInvoiceVNPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "id", nullable = true)
    private CaringOrders orders;

    @ManyToOne
    @JoinColumn(name = "customerID", referencedColumnName = "id", nullable = true)
    private Users user;

    @Column(name = "vnpResponseCode")
    private String vnpResponseCode;

    @Column(name = "status", columnDefinition = "NVARCHAR(255)")
    private String status;

    @Column(name = "vnp_TransactionNo", nullable = true)
    private long vnpTransactionNo;

    @Column(name = "vnp_InvoiceCode") // mã hóa đơn
    private long vnp_InvoiceCode;

    @Column(name = "vnp_Amount")
    private long vnpAmount;
}

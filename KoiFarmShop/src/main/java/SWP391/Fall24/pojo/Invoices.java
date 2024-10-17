package SWP391.Fall24.pojo;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Invoices")
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "orderID", referencedColumnName = "id", nullable = true)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "id", nullable = true)
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

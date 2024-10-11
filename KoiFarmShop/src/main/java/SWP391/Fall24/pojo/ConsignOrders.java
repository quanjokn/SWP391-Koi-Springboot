package SWP391.Fall24.pojo;

import SWP391.Fall24.pojo.Enum.ConsignOrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.descriptor.jdbc.NVarcharJdbcType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name = "ConsignOrders")
public class ConsignOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "customerID")
    private Users user;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "staffID", nullable = true)
    private Users staff;

    @Column(name = "date", nullable = true)
    private LocalDate date;

    @Column(name = "totalPrice", nullable = true)
    private float totalPrice;

    @Column(name = "commission", nullable = true)
    private float commission;

    @OneToMany(mappedBy = "consignOrder")
    private Set<ConsignedKois> consignedKois = new HashSet<>();

    @Column(columnDefinition = "NVARCHAR(4000)", nullable = true, length = 4000)
    private String note;

    @Column(name = "status")
    private ConsignOrderStatus status = ConsignOrderStatus.Pending_confirmation;

}

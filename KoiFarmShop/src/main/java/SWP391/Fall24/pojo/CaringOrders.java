package SWP391.Fall24.pojo;

import SWP391.Fall24.pojo.Enum.CaringOrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name = "CaringOrders")
public class CaringOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "totalPrice", nullable = false)
    private float totalPrice;

    @ManyToOne
    @JoinColumn(name = "customerID", referencedColumnName = "id")
    private Users customer;

    @ManyToOne
    @JoinColumn(name = "staffID", nullable = true, referencedColumnName = "id")
    private Users staff;

    @OneToMany
    @JoinColumn(name = "serviceID")
    private Set<CaredKois> caredKois = new HashSet<>();

    @Column(name = "status", nullable = true)
    private String status = CaringOrderStatus.Pending_confirmation.toString();

    @Column(columnDefinition = "NVARCHAR(4000)", nullable = true, length = 4000)
    private String note;

}

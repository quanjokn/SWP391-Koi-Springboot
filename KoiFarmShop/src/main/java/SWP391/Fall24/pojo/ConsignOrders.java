package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name = "customerID",nullable = false)
    private Users user;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "staffID",nullable = false)
    private Users staff;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "totalPrice", nullable = false)
    private float totalPrice;

    @Column(name = "commission", nullable = false)
    private float commission;

    @OneToMany(mappedBy = "consignOrder")
    private Set<ConsignedKois> consignedKois = new HashSet<ConsignedKois>();

    @Column
    private boolean status = false;

}

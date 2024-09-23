package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Data;
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
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceID", referencedColumnName = "id")
    private ServiceOrders serviceOrder;

    @Column(nullable = false)
    private int customerID;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "totalPrice", nullable = false)
    private float totalPrice;

    @Column(name = "commission", nullable = false)
    private float commission;

    @OneToMany(mappedBy = "consignOrder")
    private Set<ConsignedKois> consignedKois = new HashSet<ConsignedKois>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsignOrders that = (ConsignOrders) o;
        return customerID == that.customerID && Float.compare(totalPrice, that.totalPrice) == 0 && Float.compare(commission, that.commission) == 0 && Objects.equals(serviceOrder, that.serviceOrder) && Objects.equals(date, that.date) && Objects.equals(consignedKois, that.consignedKois);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceOrder, customerID, date, totalPrice, commission, consignedKois);
    }
}

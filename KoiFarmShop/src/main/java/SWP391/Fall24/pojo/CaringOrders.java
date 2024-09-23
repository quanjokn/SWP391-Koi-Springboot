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
@Table(name = "CaringOrders")
public class CaringOrders {
    @Id
    @OneToOne
    @JoinColumn (name = "serviceID", referencedColumnName = "id")
    private ServiceOrders serviceOrder;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "totalPrice", nullable = false)
    private float totalPrice;

    @Column(name = "customerID", nullable = false)
    private int customerID;

    @OneToMany (cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceID")
    private Set<CaredKois> caredKois = new HashSet<CaredKois>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaringOrders that = (CaringOrders) o;
        return Float.compare(totalPrice, that.totalPrice) == 0 && customerID == that.customerID && Objects.equals(serviceOrder, that.serviceOrder) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(caredKois, that.caredKois);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceOrder, startDate, endDate, totalPrice, customerID, caredKois);
    }
}

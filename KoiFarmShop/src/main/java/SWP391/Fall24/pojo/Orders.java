package SWP391.Fall24.pojo;

import SWP391.Fall24.pojo.Enum.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Setter
@Getter
@Entity
@Table(name = "Orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customerID", referencedColumnName = "id")
    private Users customer;

    @ManyToOne
    @JoinColumn(name = "staffID", referencedColumnName = "id")
    private Users staff;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderID")
    private Set<OrderDetails> orderDetails = new HashSet<OrderDetails>();

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "total", nullable = false)
    private float total;

    @Column(name = "status", nullable = true )
    private String status = (OrderStatus.Pending_confirmation).toString();

    @Column(name = "payment")
    private String payment;

    @Column(columnDefinition = "NVARCHAR(4000)", nullable = true, length = 4000)
    private String note;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dateId")
    private OrderDateStatus orderDateStatus = new OrderDateStatus();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id == orders.id && Float.compare(total, orders.total) == 0 && Objects.equals(customer, orders.customer) && Objects.equals(staff, orders.staff) && Objects.equals(orderDetails, orders.orderDetails) && Objects.equals(date, orders.date) && Objects.equals(status, orders.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, staff, orderDetails, date, total, status);
    }
}

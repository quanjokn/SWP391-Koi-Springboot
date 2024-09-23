package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
    private Date date;

    @Column(name = "total", nullable = false)
    private float total;

    @Column(name = "status", nullable = false)
    private String status; // 'Confirming' 'Deliverying' 'Completed'

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

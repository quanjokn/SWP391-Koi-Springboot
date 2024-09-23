package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Data
@Setter
@Getter
@Entity
@Table(name = "OrderDetails")
public class OrderDetails {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderID")
    private Orders orders;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fishID")
    private Fishes fishes;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private float discount = 0;

    @Column(nullable = false)
    private float total;

    @Column(nullable = false)
    private float rating;

    @Column(nullable = false)
    private String feedback;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return quantity == that.quantity && Float.compare(price, that.price) == 0 && Float.compare(discount, that.discount) == 0 && Float.compare(total, that.total) == 0 && Float.compare(rating, that.rating) == 0 && Objects.equals(orders, that.orders) && Objects.equals(fishes, that.fishes) && Objects.equals(feedback, that.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, fishes, quantity, price, discount, total, rating, feedback);
    }
}

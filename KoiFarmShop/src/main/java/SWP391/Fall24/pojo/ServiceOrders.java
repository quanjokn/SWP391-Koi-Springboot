package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ServiceOrders")
public class ServiceOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "customerID",nullable = false)
    private Users user;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "staffID",nullable = false)
    private Users staff;

    @Column(length = 50, nullable = false)
    private String category; // 'Care' or 'Consign'

    @Column
    private boolean status = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceOrders that = (ServiceOrders) o;
        return id == that.id && status == that.status && Objects.equals(user, that.user) && Objects.equals(staff, that.staff) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, staff, category, status);
    }
}

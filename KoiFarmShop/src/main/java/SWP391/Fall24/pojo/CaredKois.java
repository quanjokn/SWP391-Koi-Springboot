package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Data
@Getter
@Setter
@Entity
@Table(name = "CaredKois")
public class CaredKois {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceID", nullable = false)
    private CaringOrders caringOrder;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String sex;

    @Column(length = 50, nullable = false)
    private String age;

    @Column(length = 50, nullable = false)
    private String size;

    @Column(length = 255, nullable = false)
    private String healthStatus;

    @Column(length = 255, nullable = false)
    private String ration;

    @Column
    private boolean status = true;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaredKois caredKois = (CaredKois) o;
        return id == caredKois.id && status == caredKois.status && Objects.equals(caringOrder, caredKois.caringOrder) && Objects.equals(name, caredKois.name) && Objects.equals(sex, caredKois.sex) && Objects.equals(age, caredKois.age) && Objects.equals(size, caredKois.size) && Objects.equals(healthStatus, caredKois.healthStatus) && Objects.equals(ration, caredKois.ration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caringOrder, name, sex, age, size, healthStatus, ration, status);
    }
}

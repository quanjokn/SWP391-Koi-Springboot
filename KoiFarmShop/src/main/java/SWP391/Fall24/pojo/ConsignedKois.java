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
@Table(name = "ConsignedKois")
public class ConsignedKois {
    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fishID", referencedColumnName = "id")
    private Fishes fishe;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 4000, nullable = false)
    private String description;

    @Column(length = 50, nullable = false)
    private String sex;

    @Column(length = 50, nullable = false)
    private String age;

    @Column(length = 255, nullable = false)
    private String character;

    @Column(length = 50, nullable = false)
    private String size;

    @Column(nullable = false)
    private float price;

    @Column(length = 255, nullable = false)
    private String healthStatus;

    @Column(length = 4000, nullable = false)
    private String ration;

    @Column(length = 50, nullable = false)
    private String photo;

    @Column(length = 50, nullable = false)
    private String video;

    @Column(length = 50, nullable = false)
    private String certificate;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "customerID", nullable = false)
    private int customerID;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceID")
    private ConsignOrders consignOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsignedKois that = (ConsignedKois) o;
        return Float.compare(price, that.price) == 0 && status == that.status && customerID == that.customerID && Objects.equals(fishe, that.fishe) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(sex, that.sex) && Objects.equals(age, that.age) && Objects.equals(character, that.character) && Objects.equals(size, that.size) && Objects.equals(healthStatus, that.healthStatus) && Objects.equals(ration, that.ration) && Objects.equals(photo, that.photo) && Objects.equals(video, that.video) && Objects.equals(certificate, that.certificate) && Objects.equals(consignOrder, that.consignOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fishe, name, description, sex, age, character, size, price, healthStatus, ration, photo, video, certificate, status, customerID, consignOrder);
    }
}

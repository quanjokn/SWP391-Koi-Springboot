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
@Table(name = "Kois")
public class Kois {
    @Id
    @OneToOne
    @JoinColumn(name = "fishID", referencedColumnName = "id")
    private Fishes fish;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

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

    @Column(length = 255, nullable = false)
    private String ration;

    @Column(length = 50, nullable = false)
    private String photo;

    @Column(length = 50)
    private String video;

    @Column(length = 50, nullable = false)
    private String certificate;

    @Column
    private boolean status = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kois kois = (Kois) o;
        return quantity == kois.quantity && Float.compare(price, kois.price) == 0 && status == kois.status && Objects.equals(fish, kois.fish) && Objects.equals(name, kois.name) && Objects.equals(description, kois.description) && Objects.equals(sex, kois.sex) && Objects.equals(age, kois.age) && Objects.equals(character, kois.character) && Objects.equals(size, kois.size) && Objects.equals(healthStatus, kois.healthStatus) && Objects.equals(ration, kois.ration) && Objects.equals(photo, kois.photo) && Objects.equals(video, kois.video) && Objects.equals(certificate, kois.certificate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fish, name, quantity, description, sex, age, character, size, price, healthStatus, ration, photo, video, certificate, status);
    }
}

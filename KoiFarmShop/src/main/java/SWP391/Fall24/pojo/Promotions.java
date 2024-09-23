package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name = "Promotions")
public class Promotions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private float discount;

    @Column(nullable = false)
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "promotionID")
    private Set<Fishes> fishes =  new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promotions that = (Promotions) o;
        return id == that.id && Float.compare(discount, that.discount) == 0 && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(fishes, that.fishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, discount, description, fishes);
    }
}

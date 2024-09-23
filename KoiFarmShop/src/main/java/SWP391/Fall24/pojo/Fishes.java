package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
@Getter
@Setter
@Table(name = "Fishes")
public class Fishes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String category; // 'Batch'/'Koi'/'ConsignedKoi'

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fishID")
    private Set<OrderDetails> orderDetails = new HashSet<>();

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "promotionID")
    private Promotions promotion;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "originID")
    private Origins origin;

    @ManyToMany (cascade = CascadeType.MERGE)
    @JoinTable(name = "SpeciesFishes",
    joinColumns = @JoinColumn(name = "fishID"),
    inverseJoinColumns = @JoinColumn(name = "speciesID"))
    private Set<Species> species = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fishes fishes = (Fishes) o;
        return id == fishes.id && Objects.equals(category, fishes.category) && Objects.equals(orderDetails, fishes.orderDetails) && Objects.equals(promotion, fishes.promotion) && Objects.equals(origin, fishes.origin) && Objects.equals(species, fishes.species);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, orderDetails, promotion, origin, species);
    }
}

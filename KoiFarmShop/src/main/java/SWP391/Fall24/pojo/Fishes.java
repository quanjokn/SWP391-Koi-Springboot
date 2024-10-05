package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Fishes")
public class Fishes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String category; // 'Batch'/'Koi'/'ConsignedKoi'

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "promotionID")
    private Promotions promotion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "originID")
    private Origins origin;

    @ManyToMany (cascade = CascadeType.MERGE)
    @JoinTable(name = "SpeciesFishes",
    joinColumns = @JoinColumn(name = "fishID"),
    inverseJoinColumns = @JoinColumn(name = "speciesID"))
    private Set<Species> species = new HashSet<>();

    @Column
    private Float rating;

}

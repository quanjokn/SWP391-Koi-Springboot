package SWP391.Fall24.pojo;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Setter
@Getter
@Entity
@Table(name = "Species")
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String name; // 'Tancho'....

    @ManyToMany(mappedBy = "species")
    private Set<Fishes> fishes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Species species = (Species) o;
        return id == species.id && Objects.equals(name, species.name) && Objects.equals(fishes, species.fishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fishes);
    }
}

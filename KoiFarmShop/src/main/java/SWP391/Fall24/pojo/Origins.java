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
@Table(name = "Origins")
public class Origins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false, columnDefinition = "NVARCHAR(255)")
    private String origin; // 'Purebred imported', 'Purebred Viet', 'F1'

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Origins origins = (Origins) o;
        return id == origins.id && Objects.equals(origin, origins.origin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, origin);
    }
}

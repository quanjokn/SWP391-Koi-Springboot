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
@Table(name = "LoyaltyPoints")
public class LoyaltyPoints {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String userID;

    @Column
    private int point = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoyaltyPoints that = (LoyaltyPoints) o;
        return userID == that.userID && point == that.point;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, point);
    }
}

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

    @OneToOne
    @JoinColumn(name = "customerID", unique = true)
    private Users customer;

    @Column(name = "userName", nullable = true)
    private String userName;

    @Column
    private int point = 0;

}

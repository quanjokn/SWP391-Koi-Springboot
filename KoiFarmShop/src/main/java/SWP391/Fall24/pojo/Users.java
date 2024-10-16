package SWP391.Fall24.pojo;

import SWP391.Fall24.pojo.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userName", unique = true)
    private String userName;

    @JsonIgnore
    @Column(length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role = Role.Customer;

    @Column(length = 255, nullable = true, columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(length = 50)
    private String phone;

    @Column(length = 255,nullable = true, columnDefinition = "NVARCHAR(255)")
    private String address;

    @Column(length = 255, columnDefinition = "NVARCHAR(255)")
    private String email;

    @Column(nullable = true)
    private boolean status = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "point", nullable = true)
    private LoyaltyPoints point;

}

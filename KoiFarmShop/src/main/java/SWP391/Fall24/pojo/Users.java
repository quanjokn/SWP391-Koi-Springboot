package SWP391.Fall24.pojo;

import SWP391.Fall24.pojo.Enum.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
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

    @Column(name = "point" ,nullable = true)
    private Integer point = 0;

    public int addPoint(int point) {
        this.point += point;
        return this.point;
    }

}

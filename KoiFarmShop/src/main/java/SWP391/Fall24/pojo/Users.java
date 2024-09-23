package SWP391.Fall24.pojo;

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

    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @Column(length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(length = 255)
    private String name;

    @Column(length = 50, nullable = false)
    private String phone;

    @Column(length = 255)
    private String address;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(nullable = false)
    private boolean status = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id && status == users.status && Objects.equals(userName, users.userName) && Objects.equals(password, users.password) && role == users.role && Objects.equals(name, users.name) && Objects.equals(phone, users.phone) && Objects.equals(address, users.address) && Objects.equals(email, users.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, role, name, phone, address, email, status);
    }
}

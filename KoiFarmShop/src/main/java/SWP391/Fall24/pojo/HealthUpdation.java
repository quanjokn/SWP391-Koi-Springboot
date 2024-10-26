package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthUpdation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caredKoiID", referencedColumnName = "id")
    private CaredKois caredKoi;

    private LocalDate date;

    private String photo;

    private String evaluation;

    private Boolean status;
}

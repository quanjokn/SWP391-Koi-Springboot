package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Data
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OrderDateStatus")
public class OrderDateStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dateId;

    @Column(nullable = true)
    private LocalDate orderDate;
    @Column(nullable = true)
    private LocalDate responseDate;
    @Column(nullable = true)
    private LocalDate deliveryDate;
    @Column(nullable = true)
    private LocalDate completeDate;
}

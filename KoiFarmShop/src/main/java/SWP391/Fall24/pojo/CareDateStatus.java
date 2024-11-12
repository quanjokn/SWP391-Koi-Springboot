package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
@Entity
@Table(name="CareDateStatus")
public class CareDateStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(nullable = true)
    private LocalDate requestDate;
    @Column(nullable = true)
    private LocalDate pendingDate;
    @Column(nullable = true)
    private LocalDate responseDate;
    @Column(nullable = true)
    private LocalDate paymentDate;
    @Column(nullable = true)
    private LocalDate completedDate;

}

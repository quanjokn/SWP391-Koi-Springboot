package SWP391.Fall24.pojo;

import SWP391.Fall24.pojo.Enum.CaredKoiStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name = "CaredKois")
public class CaredKois {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceID")
    private CaringOrders caringOrder;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String name;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String sex;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String age;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String size;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String healthStatus;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String ration;

    @Column
    private String photo;

    @Column(name = "status", nullable = true)
    private String status = CaredKoiStatus.Pending_confirmation.toString();

}

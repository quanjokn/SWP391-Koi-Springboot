package SWP391.Fall24.pojo;

import SWP391.Fall24.pojo.Enum.ConsignedKoiStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Data
@Getter
@Setter
@Entity
@Table(name = "ConsignedKois")
public class ConsignedKois {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fishID", referencedColumnName = "id")
    private Fishes fish;

    @Id
    @Column(name = "id")
    private int id;

    @Column(length = 255, columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(name = "quantity", nullable = true)
    private Integer quantity = 1;

    @Column(length = 4000, columnDefinition = "NVARCHAR(4000)")
    private String description;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String sex;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String age;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String character;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String size;

    @Column(nullable = false)
    private float price;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String healthStatus;

    @Column(length = 1000, columnDefinition = "NVARCHAR(1000)")
    private String ration;

    @Column(length = 50)
    private String photo;

    @Column(length = 50)
    private String video;

    @Column(length = 50)
    private String certificate;

    @Column(name = "status")
    private String status = ConsignedKoiStatus.Pending_confirmation.toString();

    @Column(name = "customerID")
    private int customerID;

    @Column(name = "type")
    private String type;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "serviceID")
    private ConsignOrders consignOrder;
}

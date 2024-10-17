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
@Table(name = "Batches")
public class Batches {

    @Id
    @Column(name = "id" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "fishID")
    private Fishes fish;

    @Column(length = 255, nullable = false, columnDefinition = "NVARCHAR(255)")
    private String name;

    @Column(length = 4000, nullable = false, columnDefinition = "NVARCHAR(4000)")
    private String description;

    @Column(length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String sex;

    @Column(length = 50, nullable = false, columnDefinition = "NVARCHAR(50)")
    private String age;

    @Column(length = 255, nullable = false, columnDefinition = "NVARCHAR(255)")
    private String character;

    @Column(length = 50, nullable = false, columnDefinition = "NVARCHAR(255)")
    private String size;

    @Column(nullable = false)
    private float price;

    @Column(length = 255, nullable = false, columnDefinition = "NVARCHAR(255)")
    private String healthStatus;

    @Column(length = 4000, nullable = false, columnDefinition = "NVARCHAR(4000)")
    private String ration;

    @Column(length = 50, nullable = false)
    private String photo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String video;

    @Column
    private boolean status = true; // false is out of stock

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Batches batches = (Batches) o;
        return Float.compare(price, batches.price) == 0 && status == batches.status && Objects.equals(fish, batches.fish) && Objects.equals(name, batches.name) && Objects.equals(description, batches.description) && Objects.equals(sex, batches.sex) && Objects.equals(age, batches.age) && Objects.equals(character, batches.character) && Objects.equals(size, batches.size) && Objects.equals(healthStatus, batches.healthStatus) && Objects.equals(ration, batches.ration) && Objects.equals(photo, batches.photo) && Objects.equals(video, batches.video);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fish, name, description, sex, age, character, size, price, healthStatus, ration, photo, video, status);
    }
}

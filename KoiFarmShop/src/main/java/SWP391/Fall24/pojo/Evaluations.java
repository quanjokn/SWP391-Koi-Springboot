package SWP391.Fall24.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Evaluations")
public class Evaluations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fishID", referencedColumnName = "id")
    private Fishes fish;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "Rating")
    private float rating;

    @Column(name = "Feedback", columnDefinition = "NVARCHAR(4000)")
    private String feedback;

    public Evaluations(Fishes fish, LocalDate date, String userName, float rating, String feedback) {
        this.fish = fish;
        this.date = date;
        this.userName = userName;
        this.rating = rating;
        this.feedback = feedback;
    }
}

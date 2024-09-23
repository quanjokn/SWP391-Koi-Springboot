package SWP391.Fall24.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="Blogs")
public class Blogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "staffID", nullable = false)
    private Users staff;

    @Column(nullable = false)
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blogs blogs = (Blogs) o;
        return id == blogs.id && Objects.equals(title, blogs.title) && Objects.equals(description, blogs.description) && Objects.equals(staff, blogs.staff) && Objects.equals(date, blogs.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, staff, date);
    }
}

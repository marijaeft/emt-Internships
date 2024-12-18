package mk.ukim.finki.wp.internships.model.internships;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class InternshipWeek {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JsonBackReference
    private Internship internship;

    private String description;
    public InternshipWeek(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public InternshipWeek(LocalDate startDate, LocalDate endDate, Internship internship, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.internship = internship;
        this.description = description;
    }
}

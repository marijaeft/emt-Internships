package mk.ukim.finki.wp.internships.model.internships;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.internships.model.Company;

@Entity
@Getter
@Setter
public class InternshipPosting {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Company company;

    private Integer plannedWeeks;

    private String position;

    private String description;
}

package mk.ukim.finki.wp.internships.model.internships;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.wp.internships.model.Professor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InternshipCoordinator {
    @Id
    private String id;

    @MapsId
    @OneToOne
    @JoinColumn(name="id")
    private Professor professor;


    public InternshipCoordinator(Professor professor) {
        this.professor = professor;
    }
}

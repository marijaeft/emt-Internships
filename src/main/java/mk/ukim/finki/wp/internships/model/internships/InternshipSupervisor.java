package mk.ukim.finki.wp.internships.model.internships;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.wp.internships.model.Company;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InternshipSupervisor {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    private Company company;

    @Column(unique = true)
    private String email;

    private String fullName;

    public InternshipSupervisor(Company company) {
        this.company = company;
    }

    public InternshipSupervisor(Company company, String email, String fullName) {
        this.company = company;
        this.email = email;
        this.fullName = fullName;
    }
}

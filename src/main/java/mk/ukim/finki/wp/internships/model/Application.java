package mk.ukim.finki.wp.internships.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.internships.model.enums.ApplicationStatus;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Application {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @UpdateTimestamp(source = SourceType.DB)
    private Timestamp applicationDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Advertisement advertisement;
    private byte[] CV;

    public Application() {
    }

    public Application(Student student, Advertisement advertisement, byte[] CV) {
        this.student = student;
        this.advertisement = advertisement;
        this.CV = CV;
    }
}

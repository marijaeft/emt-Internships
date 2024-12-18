package mk.ukim.finki.wp.internships.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProfessorNotFoundException extends EntityNotFoundException {
    public ProfessorNotFoundException(String professorId) {
        super("Professor with id " + professorId + " not found");
    }
}


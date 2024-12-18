package mk.ukim.finki.wp.internships.exception;

public class StudentNotFoundException extends EntityNotFoundException{
    public StudentNotFoundException(String studentId) {
        super("Student with id " + studentId + " not found");
    }
}

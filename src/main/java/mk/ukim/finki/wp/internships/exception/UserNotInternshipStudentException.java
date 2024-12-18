package mk.ukim.finki.wp.internships.exception;

public class UserNotInternshipStudentException extends BadRequestException {
    public UserNotInternshipStudentException(String studentId, Long internshipId) {
        super("Internship with id " + internshipId + " does not have student with id " + studentId);
    }
}

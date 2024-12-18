package mk.ukim.finki.wp.internships.exception;

public class SupervisorNotFoundException extends EntityNotFoundException{
    public SupervisorNotFoundException(String supervisorId) {
        super("Internship supervisor with id " + supervisorId + " not found");
    }
}

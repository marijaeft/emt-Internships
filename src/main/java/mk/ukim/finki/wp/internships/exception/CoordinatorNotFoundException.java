package mk.ukim.finki.wp.internships.exception;

public class CoordinatorNotFoundException extends EntityNotFoundException{
    public CoordinatorNotFoundException(Long coordinatorId) {
        super("Internship coordinator with id " + coordinatorId + " not found");
    }
}

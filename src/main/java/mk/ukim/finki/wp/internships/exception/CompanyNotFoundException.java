package mk.ukim.finki.wp.internships.exception;

public class CompanyNotFoundException extends EntityNotFoundException {
    public CompanyNotFoundException(String entityId) {
        super("Company with id " + entityId + " not found");
    }
}
package mk.ukim.finki.wp.internships.service;

import mk.ukim.finki.wp.internships.model.internships.InternshipCoordinator;

public interface InternshipCoordinatorService {
    InternshipCoordinator create(String professorId);

    InternshipCoordinator delete(String professorId);

    InternshipCoordinator findById(String professorId);

    void approveInternship(String professorId, Long internshipId);

    void revokeApprovalInternship(String professorId, Long internshipId);

    void assignRandom(Long internshipId);
}

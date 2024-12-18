package mk.ukim.finki.wp.internships.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.exception.*;
import mk.ukim.finki.wp.internships.model.Professor;
import mk.ukim.finki.wp.internships.model.internships.Internship;
import mk.ukim.finki.wp.internships.model.internships.InternshipCoordinator;
import mk.ukim.finki.wp.internships.model.internships.InternshipStatus;
import mk.ukim.finki.wp.internships.repository.ProfessorRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipCoordinatorRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipRepository;
import mk.ukim.finki.wp.internships.service.InternshipCoordinatorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class InternshipCoordinatorServiceImpl implements InternshipCoordinatorService {
    private final InternshipCoordinatorRepository coordinatorRepository;
    private final InternshipRepository internshipRepository;
    private final ProfessorRepository professorRepository;

    @Override
    public InternshipCoordinator create(String professorId) {
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new ProfessorNotFoundException(professorId));

        internshipRepository.findAllByProfessorIdAndStatus(null, InternshipStatus.PENDING_PROFFESSOR_REVIEW)
                .forEach(i -> {
                    i.setProfessor(professor);
                    internshipRepository.save(i);
                });

        InternshipCoordinator coordinator = new InternshipCoordinator(professor);
        return coordinatorRepository.save(coordinator);
    }

    @Override
    public InternshipCoordinator delete(String professorId) {
        InternshipCoordinator coordinator = findById(professorId);
        coordinatorRepository.delete(coordinator);
        return coordinator;
    }

    @Override
    public InternshipCoordinator findById(String professorId) {
        return coordinatorRepository.findById(professorId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void changeInternshipStatus(String professorId, Long internshipId, InternshipStatus from, InternshipStatus to) {
        Internship internship = internshipRepository.findById(internshipId).orElseThrow(() -> new InternshipNotFoundException(internshipId));

        if (internship.getStatus() != from) {
            throw new IllegalInternshipStatusOperation(from, to);
        }

        if (!internship.getProfessor().getId().equals(professorId)) {
            throw new UserNotInternshipCoordinatorException(professorId,internshipId);
        }

        internship.setStatus(to);
        internshipRepository.save(internship);
    }

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#professorId)")
    public void approveInternship(String professorId, Long internshipId) {
        changeInternshipStatus(professorId,internshipId,InternshipStatus.PENDING_PROFFESSOR_REVIEW, InternshipStatus.DEPOSITED);
    }

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#professorId)")
    public void revokeApprovalInternship(String professorId, Long internshipId) {
        changeInternshipStatus(professorId,internshipId,InternshipStatus.DEPOSITED, InternshipStatus.PENDING_PROFFESSOR_REVIEW);
    }

    @Override
    public void assignRandom(Long internshipId) {
        Internship internship = internshipRepository
                .findById(internshipId)
                .orElseThrow(() -> new InternshipNotFoundException(internshipId));

        Random random = new Random();
        List<InternshipCoordinator> coordinators = coordinatorRepository.findAll();

        InternshipCoordinator coordinator = new InternshipCoordinator();
        if (!coordinators.isEmpty()) {
            coordinator = coordinators.get(random.nextInt(0,coordinators.size()));
        }

        internship.setProfessor(coordinator.getProfessor());
        internshipRepository.save(internship);
    }
}

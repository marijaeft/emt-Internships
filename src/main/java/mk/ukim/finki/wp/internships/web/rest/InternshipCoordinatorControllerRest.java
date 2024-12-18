package mk.ukim.finki.wp.internships.web.rest;

import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import mk.ukim.finki.wp.internships.model.Professor;
import mk.ukim.finki.wp.internships.model.internships.InternshipCoordinator;
import mk.ukim.finki.wp.internships.service.InternshipCoordinatorService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/internship-coordinators")
@CrossOrigin("*")
public class InternshipCoordinatorControllerRest {

    private final InternshipCoordinatorService coordinatorService;

    public InternshipCoordinatorControllerRest(InternshipCoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @PostMapping("/admin/create")
    public InternshipCoordinator createInternshipCoordinator(@RequestParam String professorId) {
        return coordinatorService.create(professorId);
    }

    @DeleteMapping("/admin/delete/{professorId}")
    public InternshipCoordinator deleteInternshipCoordinator(@PathVariable String professorId) {
        return coordinatorService.delete(professorId);
    }

    @GetMapping("/{professorId}")
    public InternshipCoordinator getInternshipCoordinator(@PathVariable String professorId) {
        return coordinatorService.findById(professorId);
    }

    @PutMapping("/approve/{professorId}/{internshipId}")
    public void approveInternship(@PathVariable String professorId, @PathVariable Long internshipId) {
        coordinatorService.approveInternship(professorId, internshipId);
    }

    @PutMapping("/revoke-approval/{professorId}/{internshipId}")
    public void revokeApprovalInternship(@PathVariable String professorId, @PathVariable Long internshipId) {
        coordinatorService.revokeApprovalInternship(professorId, internshipId);
    }

    @PostMapping("/opt-in")
    public Professor optIn(@AuthenticationPrincipal FacultyUserDetails principal) {
        Professor professor = principal.getProfessor();
        try {
            InternshipCoordinator coordinator = coordinatorService.findById(professor.getId());
        }
        catch (ResponseStatusException e) {
            coordinatorService.create(professor.getId());
        }
        return professor;
    }

    @PostMapping("/opt-out")
    public Professor optOut(@AuthenticationPrincipal FacultyUserDetails principal) {
        Professor professor = principal.getProfessor();
        InternshipCoordinator coordinator = coordinatorService.findById(professor.getId());
        if (coordinator != null) {
            coordinatorService.delete(professor.getId());
        }
        return professor;
    }
}

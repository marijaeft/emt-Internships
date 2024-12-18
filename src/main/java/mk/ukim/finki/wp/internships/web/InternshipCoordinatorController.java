package mk.ukim.finki.wp.internships.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import mk.ukim.finki.wp.internships.model.Professor;
import mk.ukim.finki.wp.internships.model.Student;
import mk.ukim.finki.wp.internships.model.internships.Internship;
import mk.ukim.finki.wp.internships.model.internships.InternshipCoordinator;
import mk.ukim.finki.wp.internships.model.internships.InternshipSupervisor;
import mk.ukim.finki.wp.internships.service.InternshipCoordinatorService;
import mk.ukim.finki.wp.internships.service.InternshipService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/coordinator")
@AllArgsConstructor
public class InternshipCoordinatorController {
    private final InternshipCoordinatorService coordinatorService;
    private final InternshipService internshipService;

    @PostMapping("/opt-in")
    public String optIn(@AuthenticationPrincipal FacultyUserDetails principal) {
        Professor professor = principal.getProfessor();
        InternshipCoordinator coordinator = coordinatorService.findById(professor.getId());
        if (coordinator == null) {
            coordinatorService.create(professor.getId());
        }
        return "redirect:/internships/";
    }

    @PostMapping("/opt-out")
    public String optOut(@AuthenticationPrincipal FacultyUserDetails principal) {
        Professor professor = principal.getProfessor();
        InternshipCoordinator coordinator = coordinatorService.findById(professor.getId());
        if (coordinator != null) {
            coordinatorService.delete(professor.getId());
        }
        return "redirect:/internships/";
    }

    @PostMapping("/{internshipId}/approve")
    public String approve(@PathVariable Long internshipId,
                          @AuthenticationPrincipal FacultyUserDetails facultyUserDetails) {
        Professor professor = facultyUserDetails.getProfessor();
        coordinatorService.approveInternship(professor.getId(), internshipId);
        Internship internship = internshipService.findById(internshipId);
        Student student = internship.getStudent();
        return "redirect:/internships/"+internshipId+"/"+student.getIndex()+"/";
    }

    @PostMapping("/{internshipId}/revoke-approval")
    public String revokeApproval(@PathVariable Long internshipId,
                                 @AuthenticationPrincipal FacultyUserDetails facultyUserDetails) {
        Professor professor = facultyUserDetails.getProfessor();
        Internship internship = internshipService.findById(internshipId);
        Student student = internship.getStudent();
        coordinatorService.revokeApprovalInternship(professor.getId(), internshipId);
        return "redirect:/internships/"+internshipId+"/"+student.getIndex()+"/";
    }
}

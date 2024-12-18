package mk.ukim.finki.wp.internships.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import mk.ukim.finki.wp.internships.model.Company;
import mk.ukim.finki.wp.internships.model.internships.InternshipSupervisor;
import mk.ukim.finki.wp.internships.service.CompanyService;
import mk.ukim.finki.wp.internships.service.InternshipCoordinatorService;
import mk.ukim.finki.wp.internships.service.InternshipSupervisorService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/supervisor")
@AllArgsConstructor
public class InternshipSupervisorController {
    private final InternshipSupervisorService supervisorService;
    private final InternshipCoordinatorService coordinatorService;
    private final CompanyService companyService;

    @PostMapping("/{internshipId}/approve")
    public String approve(@PathVariable Long internshipId,
                          @AuthenticationPrincipal FacultyUserDetails facultyUserDetails) {
        InternshipSupervisor supervisor = facultyUserDetails.getSupervisor();
        supervisorService.approveInternship(supervisor.getId(), internshipId);
        coordinatorService.assignRandom(internshipId);
        return "redirect:/internships/";
    }

    @PostMapping("/{internshipId}/revoke-approval")
    public String revokeApproval(@PathVariable Long internshipId,
                                 @AuthenticationPrincipal FacultyUserDetails facultyUserDetails) {
        InternshipSupervisor supervisor = facultyUserDetails.getSupervisor();
        supervisorService.revokeApprovalInternship(supervisor.getId(), internshipId);
        return "redirect:/internships/?filter=completed";
    }

    @PostMapping("/{internshipId}/assign")
    public String assign(@PathVariable Long internshipId,
                         @AuthenticationPrincipal FacultyUserDetails facultyUserDetails) {
        InternshipSupervisor supervisor = facultyUserDetails.getSupervisor();
        supervisorService.assign(supervisor.getId(), internshipId);
        return "redirect:/internships/?filter=completed";
    }

    @GetMapping("/")
    public String getAllSupervisors(Model model) {
        List<InternshipSupervisor> supervisors = supervisorService.findAll();
        model.addAttribute("supervisors", supervisors);
        return "supervisor/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("supervisor", new InternshipSupervisor());
        model.addAttribute("companies", companies);
        return "supervisor/form";
    }

    @PostMapping("/create")
    public String createSupervisor(@ModelAttribute InternshipSupervisor supervisor) {
        supervisorService.create(supervisor.getCompany().getId(),
                supervisor.getEmail(),
                supervisor.getFullName());
        return "redirect:/supervisor/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        InternshipSupervisor supervisor = supervisorService.findById(id);
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("supervisor", supervisor);
        model.addAttribute("companies", companies);
        return "supervisor/form";
    }

    @PostMapping("/edit/{id}")
    public String updateSupervisor(@PathVariable String id, @ModelAttribute InternshipSupervisor supervisor) {
        supervisor.setId(id);
        supervisorService.update(supervisor);
        return "redirect:/supervisor/";
    }


}

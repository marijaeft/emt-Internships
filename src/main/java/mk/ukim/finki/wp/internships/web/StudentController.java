package mk.ukim.finki.wp.internships.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import mk.ukim.finki.wp.internships.model.Student;
import mk.ukim.finki.wp.internships.service.StudentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/{internshipId}/approve/{index}")
    public String approve(@PathVariable Long internshipId,
                          @PathVariable Long index,
                          @AuthenticationPrincipal FacultyUserDetails facultyUserDetails) {
        Student student = facultyUserDetails.getStudent();
        studentService.approveInternship(student.getIndex(), internshipId);
        return "redirect:/internships/"+internshipId+"/"+index+"/";
    }

    @PostMapping("/{internshipId}/revoke-approval/{index}")
    public String revokeApproval(@PathVariable Long internshipId,
                                 @PathVariable Long index,
                                 @AuthenticationPrincipal FacultyUserDetails facultyUserDetails) {
        Student student = facultyUserDetails.getStudent();
        studentService.revokeApprovalInternship(student.getIndex(), internshipId);
        return "redirect:/internships/"+internshipId+"/"+index+"/";
    }
}

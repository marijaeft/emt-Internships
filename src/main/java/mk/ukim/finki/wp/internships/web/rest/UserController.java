package mk.ukim.finki.wp.internships.web.rest;

import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import mk.ukim.finki.wp.internships.model.Professor;
import mk.ukim.finki.wp.internships.model.Student;
import mk.ukim.finki.wp.internships.model.internships.InternshipSupervisor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/api/user")
    public ResponseEntity<Map<String, Object>> getUserDetails(@AuthenticationPrincipal FacultyUserDetails principal) {
        Map<String, Object> userDetails = new HashMap<>();

        Student student = principal.getStudent();
        InternshipSupervisor supervisor = principal.getSupervisor();
        Professor professor = principal.getProfessor();

        if (student != null) {
            System.out.println("vlaga");
            userDetails.put("student", student);

        } else if (supervisor != null) {
            userDetails.put("supervisor", supervisor);

        } else if (professor != null) {
            userDetails.put("professor", professor);
        }

        return ResponseEntity.ok(userDetails);
    }

}

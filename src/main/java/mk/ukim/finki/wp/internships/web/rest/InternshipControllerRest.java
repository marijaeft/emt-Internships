package mk.ukim.finki.wp.internships.web.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.model.internships.Internship;
import mk.ukim.finki.wp.internships.model.internships.InternshipStatus;
import mk.ukim.finki.wp.internships.service.InternshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internships")
@AllArgsConstructor
public class InternshipControllerRest {
    private final InternshipService internshipService;

    @PostMapping
    public ResponseEntity<Internship> createInternship(@RequestParam String studentId, @RequestParam Long postingId) {
        Internship internship = internshipService.create(studentId, postingId);
        return ResponseEntity.ok(internship);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin("http://localhost:3000")
    public ResponseEntity<Internship> deleteInternship(@PathVariable Long id) {
        Internship internship = internshipService.delete(id);
        return ResponseEntity.ok(internship);
    }

    @GetMapping("/student/{studentIndex}")
    public ResponseEntity<List<Internship>> getInternshipsByStudentIndex(@PathVariable String studentIndex) {
        List<Internship> internships = internshipService.findAllByStudentIndex(studentIndex);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/supervisor/{supervisorId}")
    public ResponseEntity<List<Internship>> getInternshipsBySupervisorId(@PathVariable String supervisorId) {
        List<Internship> internships = internshipService.findAllBySupervisorId(supervisorId);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<Internship>> getInternshipsByProfessorId(@PathVariable String professorId) {
        List<Internship> internships = internshipService.findAllByProfessorId(professorId);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/student/{studentId}/status/{status}")
    public ResponseEntity<List<Internship>> getInternshipsByStudentIdAndStatus(@PathVariable String studentId, @PathVariable InternshipStatus status) {
        List<Internship> internships = internshipService.findAllByStudentIdAndStatus(studentId, status);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/supervisor/{supervisorId}/status/{status}")
    public ResponseEntity<List<Internship>> getInternshipsBySupervisorIdAndStatus(@PathVariable String supervisorId, @PathVariable InternshipStatus status) {
        List<Internship> internships = internshipService.findAllBySupervisorIdAndStatus(supervisorId, status);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/supervisor/{supervisorId}/order-by-status")
    public ResponseEntity<List<Internship>> getInternshipsBySupervisorIdOrderByStatus(@PathVariable String supervisorId) {
        List<Internship> internships = internshipService.findAllBySupervisorIdOrderByStatusAsc(supervisorId);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/professor/{professorId}/status/{status}")
    public ResponseEntity<List<Internship>> getInternshipsByProfessorIdAndStatus(@PathVariable String professorId, @PathVariable InternshipStatus status) {
        List<Internship> internships = internshipService.findAllByProfessorIdAndStatus(professorId, status);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@internshipSecurityService.checkIfAnyInternshipUserIdAndAuthIdMatch(#id)")
    public ResponseEntity<Internship> getInternshipById(@PathVariable Long id) {
        Internship internship = internshipService.findById(id);
        return ResponseEntity.ok(internship);
    }

    @PostMapping("/{id}/weeks/{weekId}")
    public ResponseEntity<Void> addInternshipWeek(@PathVariable Long id, @PathVariable Long weekId) {
        internshipService.addInternshipWeek(id, weekId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Internship>> getInternshipsByCompanyId(@PathVariable String companyId) {
        List<Internship> internships = internshipService.findAllByPostingCompanyId(companyId);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/company/{companyId}/status/{status}")
    public ResponseEntity<List<Internship>> getInternshipsByCompanyIdAndStatus(@PathVariable String companyId, @PathVariable InternshipStatus status) {
        List<Internship> internships = internshipService.findAllByPostingCompanyIdAndStatus(companyId, status);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/admin/company/{companyId}/supervisor/not/{supervisorId}")
    public ResponseEntity<List<Internship>> getInternshipsByCompanyIdAndSupervisorIdNot(@PathVariable String companyId, @PathVariable String supervisorId) {
        List<Internship> internships = internshipService.findAllByPostingCompanyIdAndSupervisorIdNot(companyId, supervisorId);
        return ResponseEntity.ok(internships);
    }

    @GetMapping("/company/{companyId}/supervisor/null")
    public ResponseEntity<List<Internship>> getInternshipsByCompanyIdAndSupervisorIdIsNull(@PathVariable String companyId) {
        List<Internship> internships = internshipService.findAllByPostingCompanyIdAndSupervisorIdIsNull(companyId);
        return ResponseEntity.ok(internships);
    }
}

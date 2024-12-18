package mk.ukim.finki.wp.internships.web.rest;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.model.Advertisement;
import mk.ukim.finki.wp.internships.model.Student;
import mk.ukim.finki.wp.internships.service.AdvertisementService;
import mk.ukim.finki.wp.internships.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin("*")

public class StudentControllerRest {
    private final StudentService studentService;
    private final AdvertisementService advertisementService;

    public StudentControllerRest(StudentService studentService, AdvertisementService advertisementService) {
        this.studentService = studentService;
        this.advertisementService = advertisementService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Student> getStudentByUserId(@PathVariable String userId) {
        Student student = studentService.getStudentByUserId(userId);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/{index}")
    public ResponseEntity<Student> getStudentByIndex(@PathVariable String index) {
        Student student = studentService.getStudentByIndex(index);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}/approve-internship/{internshipId}")
    public ResponseEntity<Void> approveInternship(@PathVariable String id, @PathVariable Long internshipId) {
        studentService.approveInternship(id, internshipId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/revoke-internship-approval/{internshipId}")
    public ResponseEntity<Void> revokeApprovalInternship(@PathVariable String id, @PathVariable Long internshipId) {
        studentService.revokeApprovalInternship(id, internshipId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String index,
            @RequestParam(required = false) MultipartFile image) {
        try {
            Student student = studentService.registerStudent(name, surname, email, phone, index, image);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error registering student: " + e.getMessage());
        }
    }
    @GetMapping("/advertisements")
    public ResponseEntity<?> getAllAdvertisements(HttpSession session) {
        List<Advertisement> advertisements = advertisementService.findAllAdvertisements();
        Student student = (Student) session.getAttribute("currentStudent");
        Map<String, Object> response = new HashMap<>();
        response.put("advertisements", advertisements);
        response.put("student", student);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> updateStudent(
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam(required = false) MultipartFile image) {
        Student updatedStudent = studentService.updateStudent(id, name, surname, email, phone, image);
        return ResponseEntity.ok(updatedStudent);
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return ResponseEntity.ok(students);
    }



}

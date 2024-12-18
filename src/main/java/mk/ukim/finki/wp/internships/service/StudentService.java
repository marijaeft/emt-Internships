package mk.ukim.finki.wp.internships.service;

import mk.ukim.finki.wp.internships.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentService {
    Student getStudentByUserId(String userId);

    Student getStudentByIndex(String index);

    void approveInternship(String id, Long internshipId);

    void revokeApprovalInternship(String id, Long internshipId);
    List<Student> findAllStudents();
    Student registerStudent(String name, String surname, String email, String phone, String index, MultipartFile image);
    Student updateStudent(String studentId,String name, String surname, String email, String phone, MultipartFile image);
}

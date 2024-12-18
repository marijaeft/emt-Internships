package mk.ukim.finki.wp.internships.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.exception.IllegalInternshipStatusOperation;
import mk.ukim.finki.wp.internships.exception.InvalidStudentIdException;
import mk.ukim.finki.wp.internships.exception.UserNotInternshipStudentException;
import mk.ukim.finki.wp.internships.model.Student;
import mk.ukim.finki.wp.internships.model.User;
import mk.ukim.finki.wp.internships.model.UserRole;
import mk.ukim.finki.wp.internships.model.internships.Internship;
import mk.ukim.finki.wp.internships.model.internships.InternshipStatus;
import mk.ukim.finki.wp.internships.repository.StudentRepository;
import mk.ukim.finki.wp.internships.repository.UserRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipRepository;
import mk.ukim.finki.wp.internships.service.StudentService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;

    @Override
    public Student getStudentByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return studentRepository.findByEmail(user.getEmail());
    }

    @Override
    public Student getStudentByIndex(String index) {
        return studentRepository.findByIndex(index);
    }

    @Override
    @PostAuthorize("@requestAndAuthIdsMatchSecurityService.check(#id) or hasRole('ROLE_ADMIN')")
    public void approveInternship(String id, Long internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElseThrow();
        if (!internship.getStudent().getIndex().equals(id)) throw new UserNotInternshipStudentException(id, internshipId);
        if (internship.getStatus() != InternshipStatus.ONGOING) {
            throw new IllegalInternshipStatusOperation(internship.getStatus(), InternshipStatus.PENDING_COMPANY_REVIEW);
        }
        internship.setStatus(InternshipStatus.PENDING_COMPANY_REVIEW);
        internshipRepository.save(internship);
    }

    @Override
    @PostAuthorize("@requestAndAuthIdsMatchSecurityService.check(#id) or hasRole('ROLE_ADMIN')")
    public void revokeApprovalInternship(String id, Long internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElseThrow();
        if (internship.getStudent().getIndex().equals(id)) throw new UserNotInternshipStudentException(id, internshipId);
        if (internship.getStatus() != InternshipStatus.PENDING_COMPANY_REVIEW) {
            throw new IllegalInternshipStatusOperation(internship.getStatus(), InternshipStatus.ONGOING);
        }
        internship.setStatus(InternshipStatus.ONGOING);
        internshipRepository.save(internship);
    }
    @Override
    public Student registerStudent(String name, String surname, String email, String phone, String index, MultipartFile image) {
        Student student = new Student();
        student.setName(name);
        student.setLastName(surname);
        student.setEmail(email);
        student.setPhone(phone);
        student.setIndex(index);
        student.setRole(UserRole.STUDENT);
        if(image!=null) {
            try {
                byte[] imageData = image.getBytes();
                student.setImage(imageData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User user = new User();
        user.setId(name);
        user.setEmail(email);
        user.setName(name);
        user.setRole(UserRole.STUDENT);
        userRepository.save(user);
        return studentRepository.save(student);
    }
    @Override
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(String studentId,String name, String surname, String email, String phone, MultipartFile image) {
        // String encodedPassword = passwordEncoder.encode(password);
        Student student = this.getStudentByIndex(studentId);
        student.setName(name);
        student.setLastName(surname);
        student.setEmail(email);
        student.setPhone(phone);
        try {
            byte[] imageData = image.getBytes();
            student.setImage(imageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentRepository.save(student);
    }



}

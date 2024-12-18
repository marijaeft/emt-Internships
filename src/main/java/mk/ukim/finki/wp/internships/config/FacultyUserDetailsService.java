package mk.ukim.finki.wp.internships.config;

import mk.ukim.finki.wp.internships.model.Professor;
import mk.ukim.finki.wp.internships.model.Student;
import mk.ukim.finki.wp.internships.model.User;
import mk.ukim.finki.wp.internships.exception.InvalidUsernameException;
import mk.ukim.finki.wp.internships.exception.ProfessorNotFoundException;
import mk.ukim.finki.wp.internships.model.UserRole;
import mk.ukim.finki.wp.internships.model.internships.InternshipSupervisor;
import mk.ukim.finki.wp.internships.repository.ProfessorRepository;
import mk.ukim.finki.wp.internships.repository.StudentRepository;
import mk.ukim.finki.wp.internships.repository.UserRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipSupervisorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FacultyUserDetailsService implements UserDetailsService {

    @Value("${system.authentication.password}")
    String systemAuthenticationPassword;

    final UserRepository userRepository;

    final StudentRepository studentRepository;

    final InternshipSupervisorRepository internshipSupervisorRepository;

    final ProfessorRepository professorRepository;

    final PasswordEncoder passwordEncoder;

    public FacultyUserDetailsService(UserRepository userRepository,
                                     StudentRepository studentRepository,
                                     InternshipSupervisorRepository supervisorRepository,
                                     ProfessorRepository professorRepository,
                                     PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.internshipSupervisorRepository = supervisorRepository;
        this.professorRepository = professorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(() -> new InvalidUsernameException("Failed to login user with username " + username));
        if (user.getRole().isProfessor()) {
            Professor professor = professorRepository.findById(username).orElseThrow(() -> new ProfessorNotFoundException(username));
            return new FacultyUserDetails(user, professor, passwordEncoder.encode(systemAuthenticationPassword));
        }
        else if (user.getRole().isStudent()) {
            Student student = studentRepository.findByEmail(user.getEmail());
            return new FacultyUserDetails(user, student, passwordEncoder.encode(systemAuthenticationPassword));
        }
        else if (user.getRole() == UserRole.SUPERVISOR) {
            InternshipSupervisor supervisor = internshipSupervisorRepository.findByEmail(user.getEmail());
            return new FacultyUserDetails(user, supervisor, passwordEncoder.encode(systemAuthenticationPassword));
        }
        else {
            return new FacultyUserDetails(user, passwordEncoder.encode(systemAuthenticationPassword));
        }
    }
}

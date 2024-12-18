package mk.ukim.finki.wp.internships.repository.internships;

import mk.ukim.finki.wp.internships.model.internships.Internship;
import mk.ukim.finki.wp.internships.model.internships.InternshipStatus;
import mk.ukim.finki.wp.internships.repository.JpaSpecificationRepository;

import java.util.List;

public interface InternshipRepository extends JpaSpecificationRepository<Internship, Long> {
    List<Internship> findAllByProfessorId(String professorId);
    List<Internship> findAllBySupervisorId(String supervisorId);
    List<Internship> findAllByStudentIndex(String studentIndex);
    List<Internship> findAllByPostingCompanyId(String companyId);
    List<Internship> findAllByPostingCompanyIdAndSupervisorIdNot(String companyId, String supervisorId);
    List<Internship> findAllByPostingCompanyIdAndSupervisorIdIsNull(String companyId);

    List<Internship> findAllBySupervisorIdOrderByStatusAsc(String supervisorId);

    List<Internship> findAllByProfessorIdAndStatus(String professorId, InternshipStatus status);
    List<Internship> findAllBySupervisorIdAndStatus(String supervisorId, InternshipStatus status);
    List<Internship> findAllByStudentIndexAndStatus(String studentIndex, InternshipStatus status);
    List<Internship> findAllByPostingCompanyIdAndStatus(String companyId, InternshipStatus status);
}

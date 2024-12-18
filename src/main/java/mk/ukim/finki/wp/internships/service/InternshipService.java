package mk.ukim.finki.wp.internships.service;

import mk.ukim.finki.wp.internships.model.internships.Internship;
import mk.ukim.finki.wp.internships.model.internships.InternshipStatus;

import java.util.List;

public interface InternshipService {
    Internship create(String studentId, Long postingId);

    Internship delete(Long id);

    List<Internship> findAllByStudentIndex(String studentId);

    List<Internship> findAllBySupervisorId(String supervisorId);

    List<Internship> findAllByProfessorId(String coordinatorId);

    List<Internship> findAllByStudentIdAndStatus(String studentId, InternshipStatus status);

    List<Internship> findAllBySupervisorIdAndStatus(String supervisorId, InternshipStatus status);

    List<Internship> findAllBySupervisorIdOrderByStatusAsc(String supervisorId);

    List<Internship> findAllByProfessorIdAndStatus(String coordinatorId, InternshipStatus status);

    Internship findById(Long id);

    void addInternshipWeek(Long id, Long weekId);

    List<Internship> findAllByPostingCompanyId(String companyId);

    List<Internship> findAllByPostingCompanyIdAndStatus(String companyId, InternshipStatus status);

    List<Internship> findAllByPostingCompanyIdAndSupervisorIdNot(String companyId, String supervisorId);

    List<Internship> findAllByPostingCompanyIdAndSupervisorIdIsNull(String companyId);

}

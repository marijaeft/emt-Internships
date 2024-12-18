package mk.ukim.finki.wp.internships.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.exception.InternshipNotFoundException;
import mk.ukim.finki.wp.internships.exception.InternshipPostingNotFoundException;
import mk.ukim.finki.wp.internships.exception.InternshipWeekNotFoundException;
import mk.ukim.finki.wp.internships.exception.StudentNotFoundException;
import mk.ukim.finki.wp.internships.model.internships.Internship;
import mk.ukim.finki.wp.internships.model.internships.InternshipStatus;
import mk.ukim.finki.wp.internships.model.internships.InternshipWeek;
import mk.ukim.finki.wp.internships.repository.StudentRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipPostingRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipWeekRepository;
import mk.ukim.finki.wp.internships.service.InternshipService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InternshipServiceImpl implements InternshipService {
    private final InternshipRepository internshipRepository;
    private final InternshipPostingRepository internshipPostingRepository;
    private final StudentRepository studentRepository;
    private final InternshipWeekRepository weekRepository;

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#studentId)")
    public Internship create(String studentId, Long postingId) {
        Internship internship = new Internship(
                studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId)),
                internshipPostingRepository.findById(postingId).orElseThrow(() -> new InternshipPostingNotFoundException(postingId))
        );

        internship.setStatus(InternshipStatus.ONGOING);

        return internshipRepository.save(internship);
    }

    @Override
    @PreAuthorize("@internshipSecurityService.checkIfStudentIdAndAuthIdMatch(#id)")
    public Internship delete(Long id) {
        Internship internship = findById(id);
        internshipRepository.delete(internship);
        return internship;
    }

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#studentIndex)")
    public List<Internship> findAllByStudentIndex(String studentIndex) {
        return internshipRepository.findAllByStudentIndex(studentIndex);
    }

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#supervisorId)")
    public List<Internship> findAllBySupervisorId(String supervisorId) {
        return internshipRepository.findAllBySupervisorId(supervisorId);
    }

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#professorId)")
    public List<Internship> findAllByProfessorId(String professorId) {
        return internshipRepository.findAllByProfessorId(professorId);
    }

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#studentId)")
    public List<Internship> findAllByStudentIdAndStatus(String studentId, InternshipStatus status) {
        return internshipRepository.findAllByStudentIndexAndStatus(studentId,status);
    }

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#supervisorId)")
    public List<Internship> findAllBySupervisorIdAndStatus(String supervisorId, InternshipStatus status) {
        return internshipRepository.findAllBySupervisorIdAndStatus(supervisorId,status);
    }

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#supervisorId)")
    public List<Internship> findAllBySupervisorIdOrderByStatusAsc(String supervisorId) {
        return internshipRepository.findAllBySupervisorIdOrderByStatusAsc(supervisorId);
    }

    @Override
    @PreAuthorize("@requestAndAuthIdsMatchSecurityService.check(#professorId)")
    public List<Internship> findAllByProfessorIdAndStatus(String professorId, InternshipStatus status) {
        return internshipRepository.findAllByProfessorIdAndStatus(professorId,status);
    }

    @Override
    public Internship findById(Long id) {
        return internshipRepository.findById(id).orElseThrow(() -> new InternshipNotFoundException(id));
    }

    @Override
    @PreAuthorize("@internshipSecurityService.checkIfAnyInternshipUserIdAndAuthIdMatch(#id) or hasRole('ROLE_ADMIN')")
    public void addInternshipWeek(Long id, Long weekId) {
        InternshipWeek week = weekRepository.findById(weekId).orElseThrow(() -> new InternshipWeekNotFoundException(weekId));

        Internship internship = findById(id);

        internship.getJournal().add(week);

        internshipRepository.save(internship);
    }

    @Override
    @PreAuthorize("@internshipSupervisorSecurityService.checkIfRequesterIsApartOfSupervisorsCompany(#companyId)")
    public List<Internship> findAllByPostingCompanyId(String companyId) {
        return internshipRepository.findAllByPostingCompanyId(companyId);
    }

    @Override
    @PreAuthorize("@internshipSupervisorSecurityService.checkIfRequesterIsApartOfSupervisorsCompany(#companyId)")
    public List<Internship> findAllByPostingCompanyIdAndStatus(String companyId, InternshipStatus status) {
        return internshipRepository.findAllByPostingCompanyIdAndStatus(companyId,status);
    }

    @Override
    @PreAuthorize("@internshipSupervisorSecurityService.checkIfRequesterIsApartOfSupervisorsCompany(#companyId)")
    public List<Internship> findAllByPostingCompanyIdAndSupervisorIdNot(String companyId, String supervisorId) {
        return internshipRepository.findAllByPostingCompanyIdAndSupervisorIdNot(companyId,supervisorId);
    }

    @Override
    @PreAuthorize("@internshipSupervisorSecurityService.checkIfRequesterIsApartOfSupervisorsCompany(#companyId)")
    public List<Internship> findAllByPostingCompanyIdAndSupervisorIdIsNull(String companyId) {
        return internshipRepository.findAllByPostingCompanyIdAndSupervisorIdIsNull(companyId);
    }


}

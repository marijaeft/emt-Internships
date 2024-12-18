package mk.ukim.finki.wp.internships.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.exception.*;
import mk.ukim.finki.wp.internships.model.Company;
import mk.ukim.finki.wp.internships.model.User;
import mk.ukim.finki.wp.internships.model.UserRole;
import mk.ukim.finki.wp.internships.model.internships.Internship;
import mk.ukim.finki.wp.internships.model.internships.InternshipStatus;
import mk.ukim.finki.wp.internships.model.internships.InternshipSupervisor;
import mk.ukim.finki.wp.internships.repository.CompanyRepository;
import mk.ukim.finki.wp.internships.repository.UserRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipSupervisorRepository;
import mk.ukim.finki.wp.internships.service.InternshipSupervisorService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InternshipSupervisorServiceImpl implements InternshipSupervisorService {
    private final InternshipSupervisorRepository supervisorRepository;
    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    public InternshipSupervisor create(String companyId, String email, String fullName) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));

        User user = new User();
        user.setId(email.split("@")[0]);
        user.setRole(UserRole.SUPERVISOR);
        user.setName(fullName);
        user.setEmail(email);
        userRepository.save(user);

        InternshipSupervisor supervisor = new InternshipSupervisor(
                company, email, fullName
        );

        return supervisorRepository.save(supervisor);
    }

    @Override
    public InternshipSupervisor findById(String id) {
        return supervisorRepository.findById(id).orElseThrow(() -> new SupervisorNotFoundException(id));
    }

    private void changeInternshipStatus(String id, Long internshipId, InternshipStatus from, InternshipStatus to) {
        Internship internship = internshipRepository.findById(internshipId).orElseThrow(() -> new InternshipNotFoundException(internshipId));

        if (internship.getStatus() != from) {
            throw new IllegalInternshipStatusOperation(from, to);
        }

        if (!internship.getSupervisor().getId().equals(id)) {
            throw new UserNotInternshipSupervisorException(id,internshipId);
        }

        internship.setStatus(to);
        internshipRepository.save(internship);
    }

    @Override
    @PostAuthorize("@requestAndAuthIdsMatchSecurityService.check(#id)")
    public void approveInternship(String id, Long internshipId) {
        changeInternshipStatus(id,internshipId,InternshipStatus.PENDING_COMPANY_REVIEW, InternshipStatus.PENDING_PROFFESSOR_REVIEW);
    }

    @Override
    @PostAuthorize("@requestAndAuthIdsMatchSecurityService.check(#id)")
    public void revokeApprovalInternship(String id, Long internshipId) {
        changeInternshipStatus(id,internshipId,InternshipStatus.PENDING_PROFFESSOR_REVIEW, InternshipStatus.PENDING_COMPANY_REVIEW);
    }

    @Override
    @PostAuthorize("@internshipSecurityService.checkIfRequesterIsApartOfInternshipCompany(#internshipId) or hasRole('ROLE_ADMIN')")
    public void assign(String id, Long internshipId) {
        Internship internship = internshipRepository.findById(internshipId).orElseThrow(() -> new InternshipNotFoundException(internshipId));
        InternshipSupervisor supervisor = supervisorRepository.findById(id).orElseThrow(() -> new SupervisorNotFoundException(id));

        if (!internship.getPosting().getCompany().id.equals(supervisor.getCompany().id)) {
            throw new SupervisorCompanyWithInternshipCompanyMismatchException(id,
                    internship.getSupervisor().getCompany().id, internshipId);
        }

        internship.setSupervisor(supervisor);
        internshipRepository.save(internship);
    }

    @Override
    public List<InternshipSupervisor> findAll() {
        return supervisorRepository.findAll();
    }

    @Override
    public void save(InternshipSupervisor supervisor) {
        supervisorRepository.save(supervisor);
    }

    @Override
    @PostAuthorize("@internshipSupervisorSecurityService.checkIfRequesterIsApartOfSupervisorsCompany(#supervisor)")
    public void update(InternshipSupervisor supervisor) {
        InternshipSupervisor current = supervisorRepository
                .findById(supervisor.getId())
                .orElseThrow();
        User user = userRepository.findById(current.getEmail().split("@")[0]).orElseThrow();

        user.setName(supervisor.getFullName());

        userRepository.save(user);
        supervisorRepository.save(supervisor);
    }
}

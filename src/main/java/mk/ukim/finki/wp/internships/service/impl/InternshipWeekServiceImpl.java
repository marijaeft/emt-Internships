package mk.ukim.finki.wp.internships.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.exception.EntityNotFoundException;
import mk.ukim.finki.wp.internships.exception.InternshipNotFoundException;
import mk.ukim.finki.wp.internships.model.internships.Internship;
import mk.ukim.finki.wp.internships.model.internships.InternshipWeek;
import mk.ukim.finki.wp.internships.repository.internships.InternshipRepository;
import mk.ukim.finki.wp.internships.repository.internships.InternshipWeekRepository;
import mk.ukim.finki.wp.internships.service.InternshipWeekService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class InternshipWeekServiceImpl implements InternshipWeekService {
    InternshipWeekRepository internshipWeekRepository;
    InternshipRepository internshipRepository;

    @Override
    public InternshipWeek findById(Long id) {
        return internshipWeekRepository.findById(id).orElseThrow();
    }

    @Override
    @PreAuthorize("@internshipSecurityService.checkIfStudentIdAndAuthIdMatch(#internshipId) or hasRole('ROLE_ADMIN')")
    public InternshipWeek create(LocalDate startDate, LocalDate endDate, Long internshipId, String description) {
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new InternshipNotFoundException(internshipId));

        InternshipWeek internshipWeek = new InternshipWeek(startDate,endDate,internship,description);

        return internshipWeekRepository.save(internshipWeek);
    }

    @Override
    @PreAuthorize("@internshipWeekSecurityService.checkIfStudentIdAndAuthIdMatch(#id) or hasRole('ROLE_ADMIN')")
    public InternshipWeek updateDescription(Long id, String description) {
        InternshipWeek week = internshipWeekRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InternshipWeek with id "+id+" not found"));
        week.setDescription(description);
        return week;
    }

    @Override
    @PreAuthorize("@internshipWeekSecurityService.checkIfStudentIdAndAuthIdMatch(#id) or hasRole('ROLE_ADMIN')")
    public InternshipWeek update(Long id, LocalDate startDate, LocalDate endDate, Long internshipId, String description) {
        InternshipWeek week = internshipWeekRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("InternshipWeek with id "+id+" not found"));
        week.setStartDate(startDate);
        week.setEndDate(endDate);
        week.setInternship(internshipRepository.findById(internshipId).orElseThrow());
        week.setDescription(description);
        return internshipWeekRepository.save(week);
    }

    @Override
    @PreAuthorize("@internshipWeekSecurityService.checkIfStudentIdAndAuthIdMatch(#id) or hasRole('ROLE_ADMIN')")
    public InternshipWeek delete(Long id) {
        InternshipWeek res = internshipWeekRepository.findById(id).orElseThrow();
        internshipWeekRepository.delete(res);
        return res;
    }
}

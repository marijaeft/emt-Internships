package mk.ukim.finki.wp.internships.service;

import mk.ukim.finki.wp.internships.model.internships.InternshipWeek;

import java.time.LocalDate;

public interface InternshipWeekService {
    InternshipWeek findById(Long id);

    InternshipWeek create(LocalDate startDate, LocalDate endDate, Long internshipId, String description);

    InternshipWeek updateDescription(Long id, String description);

    InternshipWeek update(Long id, LocalDate startDate, LocalDate endDate, Long internshipId, String description);

    InternshipWeek delete(Long id);
}

package mk.ukim.finki.wp.internships.web.rest;

import mk.ukim.finki.wp.internships.model.internships.InternshipWeek;
import mk.ukim.finki.wp.internships.service.InternshipWeekService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/internship-weeks")
@CrossOrigin("*")
public class InternshipWeekControllerRest {

    private final InternshipWeekService internshipWeekService;

    public InternshipWeekControllerRest(InternshipWeekService internshipWeekService) {
        this.internshipWeekService = internshipWeekService;
    }

    @GetMapping("/{id}")
    public InternshipWeek getInternshipWeek(@PathVariable Long id) {
        return internshipWeekService.findById(id);
    }


    @PostMapping("/create-with-internship")
    public InternshipWeek createInternshipWeekWithInternship(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam Long internshipId,
            @RequestParam String description
    ) {
        return internshipWeekService.create(startDate, endDate, internshipId, description);
    }

    @PutMapping("/{id}")
    public InternshipWeek updateInternshipWeek(
            @PathVariable Long id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam Long internshipId,
            @RequestParam String description
    ) {
        return internshipWeekService.update(id, startDate, endDate, internshipId, description);
    }

    @PutMapping("/{id}/update-description")
    public InternshipWeek updateWeekDescription(
            @PathVariable Long id,
            @RequestParam String description
    ) {
        return internshipWeekService.updateDescription(id, description);
    }

    @DeleteMapping("/{id}")
    public InternshipWeek deleteInternshipWeek(@PathVariable Long id) {
        return internshipWeekService.delete(id);
    }
}

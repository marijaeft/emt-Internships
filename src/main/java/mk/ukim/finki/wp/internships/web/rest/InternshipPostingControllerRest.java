package mk.ukim.finki.wp.internships.web.rest;

import mk.ukim.finki.wp.internships.model.internships.InternshipPosting;
import mk.ukim.finki.wp.internships.service.InternshipPostingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/internship-postings")
public class InternshipPostingControllerRest {

    private final InternshipPostingService internshipPostingService;

    public InternshipPostingControllerRest(InternshipPostingService internshipPostingService) {
        this.internshipPostingService = internshipPostingService;
    }

    @GetMapping
    public List<InternshipPosting> getAllInternshipPostings() {
        return internshipPostingService.findAll();
    }
}

package mk.ukim.finki.wp.internships.service.security;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.service.InternshipService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InternshipCoordinatorSecurityService {
    private final InternshipService internshipService;


}

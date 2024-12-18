package mk.ukim.finki.wp.internships.service.impl;

import mk.ukim.finki.wp.internships.model.internships.InternshipPosting;
import mk.ukim.finki.wp.internships.repository.internships.InternshipPostingRepository;
import mk.ukim.finki.wp.internships.service.InternshipPostingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternshipPostingServiceImpl implements InternshipPostingService {
    private final InternshipPostingRepository internshipPostingRepository;

    public InternshipPostingServiceImpl(InternshipPostingRepository internshipPostingRepository) {
        this.internshipPostingRepository = internshipPostingRepository;
    }

    @Override
    public List<InternshipPosting> findAll() {
        return internshipPostingRepository.findAll();
    }
}

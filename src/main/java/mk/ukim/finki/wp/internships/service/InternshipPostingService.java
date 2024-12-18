package mk.ukim.finki.wp.internships.service;

import mk.ukim.finki.wp.internships.model.internships.InternshipPosting;

import java.util.List;

public interface InternshipPostingService {
    List<InternshipPosting> findAll();
}

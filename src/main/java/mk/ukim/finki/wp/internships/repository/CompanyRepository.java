package mk.ukim.finki.wp.internships.repository;

import mk.ukim.finki.wp.internships.exception.CompanyNotFoundException;
import mk.ukim.finki.wp.internships.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, String> {
}

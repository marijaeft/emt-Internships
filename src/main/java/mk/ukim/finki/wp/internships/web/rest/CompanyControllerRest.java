package mk.ukim.finki.wp.internships.web.rest;

import mk.ukim.finki.wp.internships.exception.CompanyNotFoundException;
import mk.ukim.finki.wp.internships.model.Company;
import mk.ukim.finki.wp.internships.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyControllerRest {

    private final CompanyService companyService;

    public CompanyControllerRest(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable String id) {
        return companyService.findById(id);
    }

    @PostMapping("/admin")
    public void saveCompany(
            @RequestParam String id,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String companyDescription,
            @RequestParam String websiteUrl,
            @RequestParam byte[] logoImage,
            @RequestParam byte[] banner,
            @RequestParam boolean active
    ) {
        companyService.save(id, name, phone, email, companyDescription, websiteUrl, logoImage, banner, active);
    }

    @PutMapping("/admin/{id}")
    public void editCompany(
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String companyDescription,
            @RequestParam String websiteUrl,
            @RequestParam byte[] logoImage,
            @RequestParam byte[] banner,
            @RequestParam boolean active
    ) {
        companyService.edit(id, name, phone, email, companyDescription, websiteUrl, logoImage, banner, active);
    }

    // Exception handler for CompanyNotFoundException
    @ExceptionHandler(CompanyNotFoundException.class)
    public String handleCompanyNotFoundException(CompanyNotFoundException ex) {
        return ex.getMessage();
    }
}

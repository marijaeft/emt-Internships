package mk.ukim.finki.wp.internships.service.security;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import mk.ukim.finki.wp.internships.model.internships.InternshipSupervisor;
import mk.ukim.finki.wp.internships.service.CompanyService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InternshipSupervisorSecurityService {
    private final CompanyService companyService;

    public boolean checkIfRequesterIsApartOfSupervisorsCompany(String companyId){
        FacultyUserDetails userDetails = SecurityUtil.getAuthUserDetails();

        return userDetails.getSupervisor() != null &&
                companyService.findById(companyId).getId().equals(userDetails.getSupervisor().getCompany().getId());
    }
}

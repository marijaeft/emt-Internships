package mk.ukim.finki.wp.internships.service.security;

import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import mk.ukim.finki.wp.internships.model.enums.AppRole;
import org.springframework.stereotype.Service;

@Service
public class RequestAndAuthIdsMatchSecurityService {
    public boolean check(String requestId) {
        FacultyUserDetails userDetails = SecurityUtil.getAuthUserDetails();

        String authId = null;
        if (userDetails.getProfessor() != null) authId = userDetails.getProfessor().getId();
        else if (userDetails.getSupervisor() != null) authId = userDetails.getSupervisor().getId();
        else if (userDetails.getStudent() != null) authId = userDetails.getStudent().getIndex();

        return userDetails.getRole().getApplicationRole().equals(AppRole.ADMIN) ||
                requestId.equals(authId);
    }
}

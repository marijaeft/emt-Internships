package mk.ukim.finki.wp.internships.service.security;

import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static FacultyUserDetails getAuthUserDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (FacultyUserDetails) authentication.getPrincipal();
    }
}

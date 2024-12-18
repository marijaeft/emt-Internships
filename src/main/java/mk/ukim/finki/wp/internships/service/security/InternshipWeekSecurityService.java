package mk.ukim.finki.wp.internships.service.security;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import mk.ukim.finki.wp.internships.service.InternshipWeekService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InternshipWeekSecurityService {
    private final InternshipWeekService internshipWeekService;

    public boolean checkIfStudentIdAndAuthIdMatch(Long internshipWeekId){
        FacultyUserDetails userDetails = SecurityUtil.getAuthUserDetails();

        return userDetails.getStudent() != null &&
                internshipWeekService.findById(internshipWeekId).getInternship().getStudent().getIndex().equals(userDetails.getStudent().getIndex());
    }

    public boolean checkIfAnyInternshipUserIdAndAuthIdMatch(Long internshipWeekId){
        FacultyUserDetails userDetails = SecurityUtil.getAuthUserDetails();

        if (userDetails.getStudent() != null){
            return internshipWeekService.findById(internshipWeekId).getInternship().getStudent()
                    .getIndex().equals(userDetails.getStudent().getIndex());
        }
        else if (userDetails.getProfessor() != null){
            return internshipWeekService.findById(internshipWeekId).getInternship().getProfessor()
                    .getId().equals(userDetails.getProfessor().getId());
        }
        else{
            return internshipWeekService.findById(internshipWeekId).getInternship().getSupervisor()
                    .getId().equals(userDetails.getSupervisor().getId());
        }
    }
}

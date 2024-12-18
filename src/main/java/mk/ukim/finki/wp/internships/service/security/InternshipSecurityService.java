package mk.ukim.finki.wp.internships.service.security;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.config.FacultyUserDetails;
import mk.ukim.finki.wp.internships.service.InternshipService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InternshipSecurityService {
    private final InternshipService internshipService;

    public boolean checkIfAnyInternshipUserIdAndAuthIdMatch(Long internshipId){
        FacultyUserDetails userDetails = SecurityUtil.getAuthUserDetails();

        if (userDetails.getStudent() != null){
            return internshipService.findById(internshipId).getStudent().getIndex().equals(userDetails.getStudent().getIndex());
        }
        else if (userDetails.getProfessor() != null){
            return internshipService.findById(internshipId).getProfessor().getId().equals(userDetails.getProfessor().getId());
        }
        else{
            return internshipService.findById(internshipId).getSupervisor().getId().equals(userDetails.getSupervisor().getId());
        }
    }

    public boolean checkIfStudentIdAndAuthIdMatch(Long internshipId){
        FacultyUserDetails userDetails = SecurityUtil.getAuthUserDetails();

        return userDetails.getStudent() != null &&
                internshipService.findById(internshipId).getStudent().getIndex().equals(userDetails.getStudent().getIndex());
    }

    public boolean checkIfProfessorIdAndAuthIdMatch(Long internshipId){
        FacultyUserDetails userDetails = SecurityUtil.getAuthUserDetails();

        return userDetails.getProfessor() != null &&
                internshipService.findById(internshipId).getProfessor().getId().equals(userDetails.getProfessor().getId());
    }

    public boolean checkIfSupervisorIdAndAuthIdMatch(Long internshipId){
        FacultyUserDetails userDetails = SecurityUtil.getAuthUserDetails();

        return userDetails.getSupervisor() != null &&
                internshipService.findById(internshipId).getSupervisor().getId().equals(userDetails.getSupervisor().getId());
    }

    public boolean checkIfRequesterIsApartOfInternshipCompany(Long internshipId){
        FacultyUserDetails userDetails = SecurityUtil.getAuthUserDetails();

        return userDetails.getSupervisor() != null &&
                internshipService.findById(internshipId).getPosting().getCompany().getId().equals(userDetails.getSupervisor().getCompany().getId());
    }
}

package mk.ukim.finki.wp.internships.service;

import mk.ukim.finki.wp.internships.model.Application;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicationService {
    //find students id and list the oglasite that the student applied
    //create (advertisement.numAplicants++)
    //update za statusot ako sakame da go smenime
    //find oglas id and list students that applied
    //find by id
    List<Application> findAllApplicationsThatTheStudentAppliedTo(String studentId);
    Application createApplication(String studentId, Long advertisementId, MultipartFile CV);
    Application updateApplication(Long id,String studentId, Long advertisementId);
    List<Application> findAllApplicationsToACertainAdvertisement(Long advertisementId);
    Application findApplicationById(Long id);
}

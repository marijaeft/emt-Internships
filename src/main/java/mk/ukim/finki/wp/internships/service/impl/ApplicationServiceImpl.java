package mk.ukim.finki.wp.internships.service.impl;

import mk.ukim.finki.wp.internships.exception.InvalidApplicationIdException;
import mk.ukim.finki.wp.internships.model.Advertisement;
import mk.ukim.finki.wp.internships.model.Application;
import mk.ukim.finki.wp.internships.model.Student;
import mk.ukim.finki.wp.internships.model.enums.ApplicationStatus;
import mk.ukim.finki.wp.internships.repository.ApplicationRepository;
import mk.ukim.finki.wp.internships.service.AdvertisementService;
import mk.ukim.finki.wp.internships.service.ApplicationService;
import mk.ukim.finki.wp.internships.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final StudentService studentService;
    private final AdvertisementService advertisementService;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, StudentService studentService, AdvertisementService advertisementService) {
        this.applicationRepository = applicationRepository;
        this.studentService = studentService;
        this.advertisementService = advertisementService;
    }

    @Override
    public List<Application> findAllApplicationsThatTheStudentAppliedTo(String studentId) {
        Student student = studentService.getStudentByIndex(studentId);
        return this.applicationRepository.findApplicationsByStudent(student);
    }

    @Override
    public Application createApplication(String studentId, Long advertisementId, MultipartFile CV) {
        Advertisement advertisement = this.advertisementService.findAdvertisementById(advertisementId);
        advertisement.setNumberOfApplicants(advertisement.getNumberOfApplicants()+1);
        Student student = studentService.getStudentByIndex(studentId);
        byte[] cv = new byte[0];
        if(CV!=null) {
            try {
                cv = CV.getBytes();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Application application1 = new Application(student, advertisement, cv);
        application1.setStatus(ApplicationStatus.SENT);
        return this.applicationRepository.save(application1);
    }

    @Override
    public Application updateApplication(Long id, String studentId, Long advertisementId) {
        Application application1 = this.findApplicationById(id);
        application1.setStatus(ApplicationStatus.SCHEDULED);
        return this.applicationRepository.save(application1);
    }

    @Override
    public List<Application> findAllApplicationsToACertainAdvertisement(Long advertisementId) {
        Advertisement advertisement = this.advertisementService.findAdvertisementById(advertisementId);
        return applicationRepository.findApplicationByAdvertisement(advertisement);
    }

    @Override
    public Application findApplicationById(Long id) {
        return applicationRepository.findById(id).orElseThrow(InvalidApplicationIdException::new);
    }
}

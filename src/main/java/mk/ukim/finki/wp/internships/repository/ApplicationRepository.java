package mk.ukim.finki.wp.internships.repository;


import mk.ukim.finki.wp.internships.model.Advertisement;
import mk.ukim.finki.wp.internships.model.Application;
import mk.ukim.finki.wp.internships.model.Student;

import java.util.List;

public interface ApplicationRepository extends JpaSpecificationRepository<Application,Long> {
   List<Application> findApplicationsByStudent(Student student);
   List<Application> findApplicationByAdvertisement(Advertisement advertisement);
}

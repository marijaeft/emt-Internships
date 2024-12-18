package mk.ukim.finki.wp.internships.repository;


import mk.ukim.finki.wp.internships.model.Student;

public interface StudentRepository extends JpaSpecificationRepository<Student, String> {
    Student findByEmail(String email);
    Student findByIndex(String index);
}

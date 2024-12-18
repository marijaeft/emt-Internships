package mk.ukim.finki.wp.internships.repository;

import mk.ukim.finki.wp.internships.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaSpecificationRepository<User, String> {

}

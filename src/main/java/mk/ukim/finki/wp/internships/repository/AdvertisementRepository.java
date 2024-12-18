package mk.ukim.finki.wp.internships.repository;


import mk.ukim.finki.wp.internships.model.Advertisement;

import java.util.List;

public interface AdvertisementRepository extends JpaSpecificationRepository<Advertisement,Long> {

    List<Advertisement> findAllByOrderByActiveDesc();
}

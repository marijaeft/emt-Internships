package mk.ukim.finki.wp.internships.web.rest;

import mk.ukim.finki.wp.internships.model.Advertisement;
import mk.ukim.finki.wp.internships.service.AdvertisementService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/student/advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public List<Advertisement> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementService.findAllAdvertisements();

        return advertisements;
    }

    @GetMapping("/{id}")
    public Advertisement getAdvertisementById(@PathVariable Long id) {
        Advertisement advertisement = advertisementService.findAdvertisementById(id);
        return advertisement;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('COMPANY')")
    public Advertisement createAdvertisement(@RequestParam String description,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Timestamp dateExpired,
                                      @RequestParam String type,
                                      @RequestParam String company) {
        Advertisement advertisement = advertisementService.createAdvertisement(description, dateExpired, type, company);
        return advertisement;
    }

    @GetMapping("/edit/{id}")
    //@PreAuthorize("hasRole('COMPANY')")
    public Advertisement showEditAdvertisementForm(@PathVariable Long id) {
        Advertisement advertisement = advertisementService.findAdvertisementById(id);
        return advertisement;
    }

    @PostMapping("/adv/edit/{id}")
   // @PreAuthorize("hasRole('COMPANY')")
    public Advertisement updateAdvertisement(@PathVariable Long id,
                                      @RequestParam String description,
                                      @RequestParam String dateExpired,
                                      @RequestParam String type,
                                      @RequestParam(required = false) Boolean active) {
        LocalDateTime dateTime = LocalDateTime.parse(dateExpired, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Timestamp timestamp1 = Timestamp.valueOf(dateTime);
        if(active == null)
        {
            active=false;
        }
        Advertisement updatedAdvertisement = advertisementService.updateAdvertisement(id, description, timestamp1, type,active);
        return updatedAdvertisement;
    }

    @PostMapping("/delete/{id}")
    // @PreAuthorize("hasRole('COMPANY')")
    public void deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
    }
}

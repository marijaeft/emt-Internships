package mk.ukim.finki.wp.internships.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.internships.exception.CompanyNotFoundException;
import mk.ukim.finki.wp.internships.exception.InvalidCompanyIdException;
import mk.ukim.finki.wp.internships.model.Advertisement;
import mk.ukim.finki.wp.internships.model.Company;
import mk.ukim.finki.wp.internships.model.enums.AdvertisementType;
import mk.ukim.finki.wp.internships.repository.AdvertisementRepository;
import mk.ukim.finki.wp.internships.repository.CompanyRepository;
import mk.ukim.finki.wp.internships.service.CompanyService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final AdvertisementRepository advertisementRepository;
    CompanyRepository companyRepository;

    public CompanyServiceImpl(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(String id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
    }

    @Override
    public void save(String id, String name, String phone, String email, String companyDescription,String websiteUrl, byte[] logoImage,byte[] banner,boolean active) {
        Company company = new Company();
        company.setId(id);
        company.setName(name);
        company.setPhone(phone);
        company.setEmail(email);
        company.setCompanyDescription(companyDescription);
        company.setWebsiteUrl(websiteUrl);
        company.setLogoImage(logoImage);
        company.setBanner(banner);
        company.setActive(active);
        this.companyRepository.save(company);
    }


    @Override
    public void edit(String id, String name, String phone, String email, String companyDescription,String websiteUrl, byte[] logoImage,byte[] banner,boolean active) {
        Company company = this.companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        company.setName(name);
        company.setPhone(phone);
        company.setEmail(email);
        company.setCompanyDescription(companyDescription);
        company.setWebsiteUrl(websiteUrl);
        company.setLogoImage(logoImage);
        company.setBanner(banner);
        company.setActive(active);
        this.companyRepository.save(company);
    }
    @Override
    public Advertisement addAdvertisement(String companyId, String description, Timestamp dateExpired, AdvertisementType type) {
        {
            Company company = companyRepository.findById(companyId).orElseThrow(InvalidCompanyIdException::new);

            Advertisement advertisement = new Advertisement();
            advertisement.setDescription(description);
            advertisement.setDateExpired(dateExpired);
            advertisement.setType(type);
            advertisement.setCompany(company);

            advertisement.setActive(true);

            return advertisementRepository.save(advertisement);
        }

    }
}

package mk.ukim.finki.wp.internships.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mk.ukim.finki.wp.internships.model.internships.InternshipPosting;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Company {

    @Id
    public String id; //archive number ??

    public String name;

    public String phone;

    public String email;

    @Column(length = 10_000)
    public String companyDescription;

    public String websiteUrl;

    public byte[] logoImage;

    public byte[] banner;

    public Boolean active;

    public Company(String name, String phone, String email, String companyDescription, String websiteUrl, byte[] logoImage, byte[] banner, Boolean active) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.companyDescription = companyDescription;
        this.websiteUrl = websiteUrl;
        this.logoImage = logoImage;
        this.banner = banner;
        this.active = active;
    }
}

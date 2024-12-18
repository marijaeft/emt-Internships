package mk.ukim.finki.wp.internships.web.rest;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.internships.model.Advertisement;
import mk.ukim.finki.wp.internships.model.Application;
import mk.ukim.finki.wp.internships.model.Student;
import mk.ukim.finki.wp.internships.service.AdvertisementService;
import mk.ukim.finki.wp.internships.service.ApplicationService;
import mk.ukim.finki.wp.internships.service.StudentService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final AdvertisementService advertisementService;
    private final StudentService studentService;

    public ApplicationController(ApplicationService applicationService, AdvertisementService advertisementService, StudentService studentService) {
        this.applicationService = applicationService;
        this.advertisementService = advertisementService;
        this.studentService = studentService;
    }

    @GetMapping("/student/{studentId}")
    public List<Application> getAllApplicationsByStudentId(@PathVariable String studentId) {
        List<Application> applications = applicationService.findAllApplicationsThatTheStudentAppliedTo(studentId);
        return applications;
    }

    @GetMapping("/form/{advertisementId}")
    public ResponseEntity<Map<String, Object>> getApplicationFormData(
            @PathVariable Long advertisementId, HttpSession session) {
        Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
        Student student = (Student) session.getAttribute("currentStudent");

        Map<String, Object> response = new HashMap<>();
        response.put("advertisement", advertisement);
        response.put("student", student);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveApplication(
            @RequestParam("advertisementIdStr") String advertisementIdStr,
            @RequestParam("studentIdStr") String studentIdStr,
            @RequestParam("cv") MultipartFile cvFile) {
        Map<String, String> response = new HashMap<>();

        try {
            Long advertisementId = Long.valueOf(advertisementIdStr.trim());
            Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
            Student student = studentService.getStudentByIndex(studentIdStr);
            if (advertisement == null || student == null) {
                response.put("error", "Advertisement or Student not found");
                return ResponseEntity.badRequest().body(response);
            }
            applicationService.createApplication(studentIdStr, advertisementId, cvFile);

            response.put("success", "Application saved successfully");
            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            response.put("error", "Invalid Advertisement or Student ID format");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("error", "Error processing CV file");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @GetMapping("/downloadCV/{applicationId}")
    public ResponseEntity<byte[]> downloadCV(@PathVariable Long applicationId) {
        Application application = applicationService.findApplicationById(applicationId);

        if (application == null || application.getCV() == null) {
            // Handle case where application or CV data is not found
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF); // Set appropriate content type

        // Optionally, you can set content disposition header to prompt download
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("cv_" + application.getId() + ".pdf").build());

        return new ResponseEntity<>(application.getCV(), headers, HttpStatus.OK);
    }

    @GetMapping("/advertisement/{advertisementId}")
    public List<Application> getAllApplicationsByAdvertisementId(@PathVariable Long advertisementId) {
        List<Application> applications = applicationService.findAllApplicationsToACertainAdvertisement(advertisementId);
        return applications;
    }

    @GetMapping("/{id}")
    public Application getApplicationById(@PathVariable Long id) {
        Application application = applicationService.findApplicationById(id);
        return application;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('STUDENT')")
    public Application createApplication(@RequestParam String studentId,
                                         @RequestParam Long advertisementId,
                                         @RequestParam(required = false) MultipartFile CV
    ) {
        Application application = applicationService.createApplication(studentId, advertisementId, CV);
        return application;

    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('STUDENT') or hasRole('COMPANY')")
    public Application showEditApplicationForm(@PathVariable Long id) {
        Application application = applicationService.findApplicationById(id);
        return application;
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('COMPANY')")
    public Application updateApplication(@PathVariable Long id,
                                    @RequestParam String studentId,
                                    @RequestParam Long advertisementId
                                   ) {
        Application updatedApplication = applicationService.updateApplication(id, studentId, advertisementId);
        return updatedApplication;
    }

    @GetMapping("/adv")
    public List<Advertisement> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementService.findAllAdvertisements();
        return advertisements;
    }

//    @PostMapping("/cv/generate")
//    public ResponseEntity<byte[]> generateSimplePDF() {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        Document document = new Document();
//
//        try {
//            PdfWriter.getInstance(document, outputStream);
//            document.open();
//            document.add(new Paragraph("Hello World!"));
//        } catch (DocumentException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        } finally {
//            document.close(); // Ensure the document is closed in the finally block
//        }
//
//        byte[] pdfBytes = outputStream.toByteArray();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("inline", "test.pdf");
//
//        // Log the size of the generated PDF
//        System.out.println("Generated PDF size: " + pdfBytes.length + " bytes");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(pdfBytes);
//    }


    @PostMapping("/cv/generate")
    public ResponseEntity<byte[]> generateCV(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String aboutMe,
            @RequestParam String address,
            @RequestParam String linkedin,
            @RequestParam String github,
            @RequestParam String education,
            @RequestParam String experience,
            @RequestParam String skills,
            @RequestParam String languages,
            @RequestParam(required = false) byte[] profileImage // Optional profile image
    ) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Set font styles
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font sectionHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
            Font textFont = new Font(Font.FontFamily.HELVETICA, 10);
            Font bulletFont = new Font(Font.FontFamily.HELVETICA, 10);

            // Add Name as Header
            Paragraph nameHeader = new Paragraph(name + " " + surname, headerFont);
            nameHeader.setAlignment(Element.ALIGN_CENTER);
            nameHeader.setSpacingAfter(20);
            document.add(nameHeader);

            // Add Profile Image (if available)
            if (profileImage != null && profileImage.length > 0) {
                try {
                    Image img = Image.getInstance(profileImage);
                    img.scaleToFit(100, 100);
                    img.setAbsolutePosition(450, 700);  // Adjust position
                    document.add(img);
                } catch (Exception e) {
                    System.err.println("Error adding profile image: " + e.getMessage());
                }
            }

            // Add Contact Information
            document.add(new Paragraph("Email: " + email, textFont));
            document.add(new Paragraph("Phone: " + phone, textFont));
            document.add(new Paragraph("Address: " + address, textFont));
            document.add(new Paragraph("LinkedIn: " + linkedin, textFont));
            document.add(new Paragraph("GitHub: " + github, textFont));
            document.add(Chunk.NEWLINE);  // Add spacing

            // Add About Me Section
            document.add(new Paragraph("About Me", sectionHeaderFont));
            document.add(new Paragraph(aboutMe, textFont));
            document.add(Chunk.NEWLINE);

            // Add Education Section
            document.add(new Paragraph("Education", sectionHeaderFont));
            addBulletList(document, education, bulletFont);

            // Add Experience Section
            document.add(new Paragraph("Experience", sectionHeaderFont));
            addBulletList(document, experience, bulletFont);

            // Add Skills Section
            document.add(new Paragraph("Skills", sectionHeaderFont));
            addBulletList(document, skills, bulletFont);

            // Add Languages Section
            document.add(new Paragraph("Languages", sectionHeaderFont));
            addBulletList(document, languages, bulletFont);

            // Close the document
            document.close();

            byte[] pdfBytes = outputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "cv.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Helper method to create a bullet list from content
    private void addBulletList(Document document, String content, Font bulletFont) throws DocumentException {
        String[] items = content.split("\\r?\\n");

        // Create an unordered list
        com.itextpdf.text.List bulletList = new com.itextpdf.text.List(false, 10);  // 'false' for unordered list
        bulletList.setListSymbol(new Chunk("\u2022 ", bulletFont));  // Use bullet symbol

        for (String item : items) {
            bulletList.add(new ListItem(new Phrase(item, bulletFont)));
        }

        document.add(bulletList);
    }


}
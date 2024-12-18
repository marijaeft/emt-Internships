package mk.ukim.finki.wp.internships.model;

import lombok.Getter;
import mk.ukim.finki.wp.internships.model.enums.AppRole;

public enum UserRole {
    STUDENT(false,false, true),
    COMPANY(false,true, false, AppRole.COMPANY),
    // professors
    PROFESSOR(true, false,false, AppRole.PROFESSOR),
    ACADEMIC_AFFAIR_VICE_DEAN(true, false,false, AppRole.ADMIN),
    SCIENCE_AND_COOPERATION_VICE_DEAN(true, false,false, AppRole.ADMIN),
    FINANCES_VICE_DEAN(true, false, false, AppRole.ADMIN),
    DEAN(true, false,false, AppRole.ADMIN),
    // staff
    STUDENT_ADMINISTRATION(false, false,false, AppRole.ADMIN),
    STUDENT_ADMINISTRATION_MANAGER(false, false,false, AppRole.ADMIN),
    FINANCE_ADMINISTRATION(false,false, false),
    FINANCE_ADMINISTRATION_MANAGER(false,false, false),
    LEGAL_ADMINISTRATION(false, false,false),
    ARCHIVE_ADMINISTRATION(false, false, false),
    ADMINISTRATION_MANAGER(false,false, false, AppRole.ADMIN),
    // external professors
    EXTERNAL(true,false, false),

    // companies' supervisors for student internships
    SUPERVISOR(false,true, false);

    private final Boolean professor;

    private final Boolean company;

    private final Boolean student;

    @Getter
    public AppRole applicationRole = AppRole.GUEST;

    UserRole(Boolean professor, Boolean company, Boolean student, AppRole role) {
        this.professor = professor;
        this.company = company;
        this.student = student;
        this.applicationRole = role;
    }

    UserRole(Boolean professor, Boolean company, Boolean student) {
        this.professor = professor;
        this.company = company;
        this.student = student;
    }

    public Boolean isProfessor() {
        return professor;
    }

    public Boolean isStudent() {
        return student;
    }

    public String roleName() {
        return "ROLE_" + this.name();
    }

}


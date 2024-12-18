package mk.ukim.finki.wp.internships.model.enums;

public enum AppRole {
    PROFESSOR, ADMIN, GUEST, COMPANY,STUDENT;


    public String roleName() {
        return "ROLE_" + this.name();
    }
}

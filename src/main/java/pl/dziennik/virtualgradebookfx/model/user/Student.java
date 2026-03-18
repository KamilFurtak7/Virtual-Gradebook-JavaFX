package pl.dziennik.virtualgradebookfx.model.user;

public class Student extends User {
    private String schoolClass;

    public Student() {
        setRole(Role.STUDENT);
    }

    public Student(String login, String password, String firstName, String lastName, String schoolClass) {
        super(login, password, firstName, lastName, Role.STUDENT);
        this.schoolClass = schoolClass;
    }

    public String getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }
}
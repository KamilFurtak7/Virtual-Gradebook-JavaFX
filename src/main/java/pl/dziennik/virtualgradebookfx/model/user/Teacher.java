package pl.dziennik.virtualgradebookfx.model.user;

public class Teacher extends User {
    private String subject;

    public Teacher() {
        setRole(Role.TEACHER);
    }

    public Teacher(String login, String password, String firstName, String lastName, String subject) {
        super(login, password, firstName, lastName, Role.TEACHER);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
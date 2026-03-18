package pl.dziennik.virtualgradebookfx.model.user;

public class Dean extends User {

    public Dean() {
        setRole(Role.DEAN);
    }

    public Dean(String login, String password, String firstName, String lastName) {
        super(login, password, firstName, lastName, Role.DEAN);
    }
}
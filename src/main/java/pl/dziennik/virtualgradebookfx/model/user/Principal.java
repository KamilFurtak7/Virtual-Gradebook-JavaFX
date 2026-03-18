package pl.dziennik.virtualgradebookfx.model.user;

public class Principal extends User {

    public Principal() {
        setRole(Role.PRINCIPAL);
    }

    public Principal(String login, String password, String firstName, String lastName) {
        super(login, password, firstName, lastName, Role.PRINCIPAL);
    }
}
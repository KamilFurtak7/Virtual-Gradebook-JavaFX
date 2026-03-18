package pl.dziennik.virtualgradebookfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.dziennik.virtualgradebookfx.app.SceneManager;
import pl.dziennik.virtualgradebookfx.model.user.Role;
import pl.dziennik.virtualgradebookfx.model.user.User;
import pl.dziennik.virtualgradebookfx.service.impl.AuthenticationServiceImpl;
import pl.dziennik.virtualgradebookfx.service.interfaces.AuthenticationService;

public class LoginController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    private final AuthenticationService authenticationService = new AuthenticationServiceImpl();

    @FXML
    private void handleLogin() {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Wypełnij login i hasło.");
            return;
        }

        User user = authenticationService.authenticate(login, password);

        if (user == null) {
            messageLabel.setText("Nieprawidłowy login lub hasło.");
            return;
        }

        if (user.getRole() == Role.STUDENT) {
            SceneManager.switchTo("/fxml/student/student-dashboard-view.fxml", "Panel ucznia");
        } else if (user.getRole() == Role.TEACHER) {
            SceneManager.switchTo("/fxml/teacher/teacher-dashboard-view.fxml", "Panel nauczyciela");
        } else if (user.getRole() == Role.PRINCIPAL) {
            SceneManager.switchTo("/fxml/principal/principal-dashboard-view.fxml", "Panel dyrektora");
        }
    }
}
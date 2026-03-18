package pl.dziennik.virtualgradebookfx.controller;

import javafx.fxml.FXML;
import pl.dziennik.virtualgradebookfx.app.SceneManager;

public class DeanDashboardController {

    @FXML
    private void handleLogout() {
        SceneManager.switchTo("/fxml/login-view.fxml", "Logowanie");
    }
}
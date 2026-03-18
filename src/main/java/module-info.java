module pl.dziennik.virtualgradebookfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports pl.dziennik.virtualgradebookfx.app;
    exports pl.dziennik.virtualgradebookfx.controller;
    exports pl.dziennik.virtualgradebookfx.model.user;
    exports pl.dziennik.virtualgradebookfx.service.interfaces;
    exports pl.dziennik.virtualgradebookfx.service.impl;
    exports pl.dziennik.virtualgradebookfx.persistence;

    opens pl.dziennik.virtualgradebookfx.app to javafx.fxml;
    opens pl.dziennik.virtualgradebookfx.controller to javafx.fxml;
}
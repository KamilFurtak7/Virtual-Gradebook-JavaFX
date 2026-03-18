package pl.dziennik.virtualgradebookfx.controller;


import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import pl.dziennik.virtualgradebookfx.app.SceneManager;
import pl.dziennik.virtualgradebookfx.model.school.Grade;
import pl.dziennik.virtualgradebookfx.model.user.Student;
import pl.dziennik.virtualgradebookfx.model.user.User;
import pl.dziennik.virtualgradebookfx.service.impl.GradeServiceImpl;
import pl.dziennik.virtualgradebookfx.service.interfaces.GradeService;
import pl.dziennik.virtualgradebookfx.util.Session;
import pl.dziennik.virtualgradebookfx.model.school.StudentSubject;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import pl.dziennik.virtualgradebookfx.model.school.TimetableEntry;
import pl.dziennik.virtualgradebookfx.service.impl.TimetableServiceImpl;
import pl.dziennik.virtualgradebookfx.service.interfaces.TimetableService;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import pl.dziennik.virtualgradebookfx.model.communication.Message;
import pl.dziennik.virtualgradebookfx.service.impl.MessageServiceImpl;
import pl.dziennik.virtualgradebookfx.service.interfaces.MessageService;
import pl.dziennik.virtualgradebookfx.model.school.Consultation;
import pl.dziennik.virtualgradebookfx.service.impl.ConsultationServiceImpl;
import pl.dziennik.virtualgradebookfx.service.interfaces.ConsultationService;

import java.util.List;

public class StudentDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private StackPane contentPane;

    private final GradeService gradeService = new GradeServiceImpl();
    private final TimetableService timetableService = new TimetableServiceImpl();
    private final MessageService messageService = new MessageServiceImpl();
    private final ConsultationService consultationService = new ConsultationServiceImpl();


    @FXML
    public void initialize() {
        User user = Session.getLoggedUser();

        if (user instanceof Student student) {
            welcomeLabel.setText("Witaj, " + student.getFullName());
            showStudentInfo();
        }
    }

    @FXML
    private void showStudentInfo() {
        User user = Session.getLoggedUser();

        if (user instanceof Student student) {
            VBox box = new VBox(12);
            box.getStyleClass().addAll("content-area", "info-card");

            Label title = new Label("Dane studenta");
            title.getStyleClass().add("section-title");

            Label fullName = new Label("Imię i nazwisko: " + student.getFullName());
            fullName.getStyleClass().add("info-text");

            Label login = new Label("Login: " + student.getLogin());
            login.getStyleClass().add("info-text");

            Label schoolClass = new Label("Klasa: " + student.getSchoolClass());
            schoolClass.getStyleClass().add("info-text");

            box.getChildren().addAll(title, fullName, login, schoolClass);
            contentPane.getChildren().setAll(box);
        }
    }

    @FXML
    private void showGrades() {
        User user = Session.getLoggedUser();

        if (user instanceof Student student) {
            List<StudentSubject> subjects = gradeService.getStudentSubjects(student.getLogin());
            double overallEctsAverage = gradeService.calculateOverallEctsAverage(student.getLogin());

            VBox mainBox = new VBox(15);
            mainBox.getStyleClass().add("content-area");

            Label title = new Label("Oceny studenta");
            title.getStyleClass().add("section-title");

            TilePane tilePane = new TilePane();
            tilePane.setHgap(16);
            tilePane.setVgap(16);
            tilePane.setPrefColumns(3);
            tilePane.getStyleClass().add("subjects-wrapper");

            for (StudentSubject subject : subjects) {
                List<Grade> subjectGrades = gradeService.getGradesForStudentAndSubject(student.getLogin(), subject.getSubjectName());

                VBox subjectTile = new VBox(8);
                subjectTile.getStyleClass().add("subject-tile");

                Label subjectTitle = new Label(subject.getSubjectName() + " (" + subject.getEcts() + " ECTS)");
                subjectTitle.getStyleClass().add("subject-title");

                subjectTile.getChildren().add(subjectTitle);

                if (subjectGrades.isEmpty()) {
                    Label noGradesLabel = new Label("Brak ocen");
                    noGradesLabel.getStyleClass().add("no-grades-label");
                    subjectTile.getChildren().add(noGradesLabel);
                } else {
                    for (Grade grade : subjectGrades) {
                        Label gradeLabel = new Label(
                                "Ocena: " + grade.getGradeValue() +
                                        " | Waga: " + grade.getGradeWeight() +
                                        "\nOpis: " + grade.getDescription()
                        );
                        gradeLabel.getStyleClass().add("subject-grade");
                        subjectTile.getChildren().add(gradeLabel);
                    }

                    double subjectAverage = gradeService.calculateWeightedAverage(subjectGrades);
                    Label averageLabel = new Label(String.format("Średnia przedmiotu: %.2f", subjectAverage));
                    averageLabel.getStyleClass().add("subject-average");
                    subjectTile.getChildren().add(averageLabel);
                }

                tilePane.getChildren().add(subjectTile);
            }

            ScrollPane scrollPane = new ScrollPane(tilePane);
            scrollPane.setFitToWidth(true);
            scrollPane.getStyleClass().add("scroll-clean");

            Label overallAverageLabel = new Label(
                    String.format("Ogólna średnia ważona ECTS: %.2f", overallEctsAverage)
            );
            overallAverageLabel.getStyleClass().add("average-label");

            mainBox.getChildren().addAll(title, scrollPane, overallAverageLabel);
            contentPane.getChildren().setAll(mainBox);
        }
    }


    @FXML
    private void showTimetable() {
        User user = Session.getLoggedUser();

        if (user instanceof Student student) {
            List<TimetableEntry> entries = timetableService.getTimetableForStudent(student.getLogin());

            String[] days = {"Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek"};
            String[] timeSlots = {"08:00-09:30", "10:00-11:30", "12:00-13:30", "14:00-15:30", "16:00-17:30"};

            VBox mainBox = new VBox(16);
            mainBox.getStyleClass().addAll("content-area", "timetable-wrapper");
            VBox.setVgrow(mainBox, Priority.ALWAYS);
            mainBox.setFillWidth(true);
            mainBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            Label title = new Label("Plan zajęć studenta");
            title.getStyleClass().add("section-title");

            GridPane grid = new GridPane();
            grid.getStyleClass().add("timetable-grid");
            grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

            ColumnConstraints timeColumn = new ColumnConstraints();
            timeColumn.setMinWidth(120);
            timeColumn.setPrefWidth(140);
            timeColumn.setHgrow(Priority.NEVER);
            timeColumn.setFillWidth(true);
            grid.getColumnConstraints().add(timeColumn);

            for (int i = 0; i < days.length; i++) {
                ColumnConstraints dayColumn = new ColumnConstraints();
                dayColumn.setHgrow(Priority.ALWAYS);
                dayColumn.setFillWidth(true);
                dayColumn.setMinWidth(140);
                grid.getColumnConstraints().add(dayColumn);
            }

            RowConstraints headerRow = new RowConstraints();
            headerRow.setMinHeight(50);
            headerRow.setPrefHeight(50);
            headerRow.setVgrow(Priority.NEVER);
            grid.getRowConstraints().add(headerRow);

            for (int i = 0; i < timeSlots.length; i++) {
                RowConstraints row = new RowConstraints();
                row.setMinHeight(110);
                row.setPrefHeight(120);
                row.setVgrow(Priority.ALWAYS);
                grid.getRowConstraints().add(row);
            }

            Label emptyCorner = new Label("Godzina");
            emptyCorner.getStyleClass().addAll("timetable-header", "timetable-header-label");
            emptyCorner.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            emptyCorner.setAlignment(Pos.CENTER);
            grid.add(emptyCorner, 0, 0);

            for (int i = 0; i < days.length; i++) {
                Label dayLabel = new Label(days[i]);
                dayLabel.getStyleClass().addAll("timetable-header", "timetable-header-label");
                dayLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                dayLabel.setAlignment(Pos.CENTER);
                dayLabel.setWrapText(true);
                grid.add(dayLabel, i + 1, 0);
            }

            for (int row = 0; row < timeSlots.length; row++) {
                VBox timeBox = new VBox();
                timeBox.getStyleClass().add("time-cell");
                timeBox.setAlignment(Pos.CENTER);
                timeBox.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                Label timeLabel = new Label(timeSlots[row]);
                timeLabel.getStyleClass().add("time-label");
                timeLabel.setWrapText(true);

                timeBox.getChildren().add(timeLabel);
                grid.add(timeBox, 0, row + 1);

                for (int col = 0; col < days.length; col++) {
                    VBox cell = new VBox(6);
                    cell.getStyleClass().add("schedule-cell");
                    cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    cell.setFillWidth(true);
                    cell.setAlignment(Pos.CENTER);

                    GridPane.setHgrow(cell, Priority.ALWAYS);
                    GridPane.setVgrow(cell, Priority.ALWAYS);

                    String currentDay = days[col];
                    String currentSlot = timeSlots[row];
                    boolean found = false;

                    for (TimetableEntry entry : entries) {
                        String entrySlot = entry.getStartTime() + "-" + entry.getEndTime();

                        if (entry.getDayOfWeek().equals(currentDay) && entrySlot.equals(currentSlot)) {
                            VBox lessonTile = new VBox(4);
                            lessonTile.getStyleClass().add("schedule-entry");
                            lessonTile.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                            lessonTile.setFillWidth(true);

                            Label subjectLabel = new Label(entry.getSubjectName());
                            subjectLabel.getStyleClass().add("schedule-subject");
                            subjectLabel.setWrapText(true);

                            Label teacherLabel = new Label("Prowadzący: " + entry.getTeacherName());
                            teacherLabel.getStyleClass().add("schedule-details");
                            teacherLabel.setWrapText(true);

                            Label roomLabel = new Label("Sala: " + entry.getRoom());
                            roomLabel.getStyleClass().add("schedule-details");
                            roomLabel.setWrapText(true);

                            Label timeEntryLabel = new Label("Godzina: " + entry.getStartTime() + " - " + entry.getEndTime());
                            timeEntryLabel.getStyleClass().add("schedule-details");
                            timeEntryLabel.setWrapText(true);

                            lessonTile.getChildren().addAll(subjectLabel, teacherLabel, roomLabel, timeEntryLabel);
                            cell.getChildren().add(lessonTile);
                            cell.setAlignment(Pos.TOP_LEFT);
                            found = true;
                        }
                    }

                    if (!found) {
                        Label emptyLabel = new Label("-");
                        emptyLabel.getStyleClass().add("empty-schedule");
                        cell.getChildren().add(emptyLabel);
                        cell.setAlignment(Pos.CENTER);
                    }

                    grid.add(cell, col + 1, row + 1);
                }
            }

            VBox.setVgrow(grid, Priority.ALWAYS);

            mainBox.getChildren().addAll(title, grid);
            contentPane.getChildren().setAll(mainBox);
        }
    }


    @FXML
    private void showMessages() {
        User user = Session.getLoggedUser();

        if (user instanceof Student student) {
            List<Message> messages = messageService.getMessagesForUser(student.getLogin());

            VBox mainBox = new VBox(14);
            mainBox.getStyleClass().addAll("content-area", "messages-wrapper");

            Label title = new Label("Wiadomości studenta");
            title.getStyleClass().add("section-title");

            if (messages.isEmpty()) {
                Label emptyLabel = new Label("Brak wiadomości.");
                emptyLabel.getStyleClass().add("info-text");
                mainBox.getChildren().addAll(title, emptyLabel);
            } else {
                mainBox.getChildren().add(title);

                for (Message message : messages) {
                    VBox messageCard = new VBox(6);

                    if (message.isRead()) {
                        messageCard.getStyleClass().add("message-card");
                    } else {
                        messageCard.getStyleClass().add("message-card-unread");
                    }

                    Label subjectLabel = new Label(message.getSubject());
                    subjectLabel.getStyleClass().add("message-subject");

                    Label metaLabel = new Label(
                            "Od: " + message.getSenderLogin() + " | Data: " + message.getSentDate() +
                                    " | Status: " + (message.isRead() ? "Przeczytana" : "Nieprzeczytana")
                    );
                    metaLabel.getStyleClass().add("message-meta");

                    Label contentLabel = new Label(message.getContent());
                    contentLabel.getStyleClass().add("message-content");
                    contentLabel.setWrapText(true);

                    messageCard.getChildren().addAll(subjectLabel, metaLabel, contentLabel);
                    mainBox.getChildren().add(messageCard);
                }
            }

            ScrollPane scrollPane = new ScrollPane(mainBox);
            scrollPane.setFitToWidth(true);
            scrollPane.getStyleClass().add("scroll-clean");

            contentPane.getChildren().setAll(scrollPane);
        }
    }

    @FXML
    private void showConsultations() {
        List<Consultation> consultations = consultationService.getAllConsultations();

        VBox mainBox = new VBox(14);
        mainBox.getStyleClass().addAll("content-area", "messages-wrapper");

        Label title = new Label("Konsultacje prowadzących");
        title.getStyleClass().add("section-title");

        if (consultations.isEmpty()) {
            Label emptyLabel = new Label("Brak konsultacji.");
            emptyLabel.getStyleClass().add("info-text");
            mainBox.getChildren().addAll(title, emptyLabel);
        } else {
            mainBox.getChildren().add(title);

            for (Consultation consultation : consultations) {
                VBox consultationCard = new VBox(6);
                consultationCard.getStyleClass().add("consultation-card");

                Label teacherLabel = new Label(consultation.getTeacherName());
                teacherLabel.getStyleClass().add("consultation-title");

                Label subjectLabel = new Label("Przedmiot: " + consultation.getSubjectName());
                subjectLabel.getStyleClass().add("consultation-text");

                Label timeLabel = new Label(
                        "Termin: " + consultation.getDayOfWeek() + ", " +
                                consultation.getStartTime() + " - " + consultation.getEndTime()
                );
                timeLabel.getStyleClass().add("consultation-text");

                Label roomLabel = new Label("Sala: " + consultation.getRoom());
                roomLabel.getStyleClass().add("consultation-text");

                consultationCard.getChildren().addAll(teacherLabel, subjectLabel, timeLabel, roomLabel);
                mainBox.getChildren().add(consultationCard);
            }
        }

        ScrollPane scrollPane = new ScrollPane(mainBox);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-clean");

        contentPane.getChildren().setAll(scrollPane);
    }


    @FXML
    private void handleLogout() {
        Session.clear();
        SceneManager.switchTo("/fxml/login-view.fxml", "Logowanie");
    }
}
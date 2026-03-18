package pl.dziennik.virtualgradebookfx.service.impl;

import pl.dziennik.virtualgradebookfx.model.user.Dean;
import pl.dziennik.virtualgradebookfx.model.user.Role;
import pl.dziennik.virtualgradebookfx.model.user.Student;
import pl.dziennik.virtualgradebookfx.model.user.Teacher;
import pl.dziennik.virtualgradebookfx.model.user.User;
import pl.dziennik.virtualgradebookfx.persistence.DatabaseConnection;
import pl.dziennik.virtualgradebookfx.service.interfaces.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY last_name, first_name";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String roleText = resultSet.getString("role");
                Role role = Role.valueOf(roleText);

                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                if (role == Role.STUDENT) {
                    String schoolClass = resultSet.getString("school_class");
                    users.add(new Student(login, password, firstName, lastName, schoolClass));
                } else if (role == Role.TEACHER) {
                    String subjectName = resultSet.getString("subject_name");
                    users.add(new Teacher(login, password, firstName, lastName, subjectName));
                } else if (role == Role.DEAN) {
                    users.add(new Dean(login, password, firstName, lastName));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
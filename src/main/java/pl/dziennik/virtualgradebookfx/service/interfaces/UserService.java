package pl.dziennik.virtualgradebookfx.service.interfaces;

import pl.dziennik.virtualgradebookfx.model.user.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
}
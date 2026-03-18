package pl.dziennik.virtualgradebookfx.service.interfaces;

import pl.dziennik.virtualgradebookfx.model.communication.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessagesForUser(String receiverLogin);
}
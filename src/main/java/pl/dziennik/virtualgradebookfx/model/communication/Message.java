package pl.dziennik.virtualgradebookfx.model.communication;

public class Message {
    private int id;
    private String senderLogin;
    private String receiverLogin;
    private String subject;
    private String content;
    private String sentDate;
    private boolean read;

    public Message() {
    }

    public Message(int id, String senderLogin, String receiverLogin, String subject, String content, String sentDate, boolean read) {
        this.id = id;
        this.senderLogin = senderLogin;
        this.receiverLogin = receiverLogin;
        this.subject = subject;
        this.content = content;
        this.sentDate = sentDate;
        this.read = read;
    }

    public int getId() {
        return id;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public String getReceiverLogin() {
        return receiverLogin;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getSentDate() {
        return sentDate;
    }

    public boolean isRead() {
        return read;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    public void setReceiverLogin(String receiverLogin) {
        this.receiverLogin = receiverLogin;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
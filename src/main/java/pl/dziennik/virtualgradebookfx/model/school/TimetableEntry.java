package pl.dziennik.virtualgradebookfx.model.school;

public class TimetableEntry {
    private int id;
    private String studentLogin;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private String subjectName;
    private String teacherName;
    private String room;

    public TimetableEntry() {
    }

    public TimetableEntry(int id, String studentLogin, String dayOfWeek, String startTime, String endTime,
                          String subjectName, String teacherName, String room) {
        this.id = id;
        this.studentLogin = studentLogin;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public String getStudentLogin() {
        return studentLogin;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getRoom() {
        return room;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentLogin(String studentLogin) {
        this.studentLogin = studentLogin;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
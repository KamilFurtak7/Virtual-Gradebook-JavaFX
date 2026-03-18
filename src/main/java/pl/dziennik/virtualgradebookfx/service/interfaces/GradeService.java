package pl.dziennik.virtualgradebookfx.service.interfaces;

import pl.dziennik.virtualgradebookfx.model.school.Grade;
import pl.dziennik.virtualgradebookfx.model.school.StudentSubject;

import java.util.List;

public interface GradeService {
    List<Grade> getGradesForStudent(String studentLogin);
    List<Grade> getGradesForStudentAndSubject(String studentLogin, String subjectName);
    List<String> getSubjectsForStudent(String studentLogin);
    List<StudentSubject> getStudentSubjects(String studentLogin);
    double calculateWeightedAverage(List<Grade> grades);
    double calculateOverallEctsAverage(String studentLogin);
}
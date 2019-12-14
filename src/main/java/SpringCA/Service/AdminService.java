package SpringCA.Service;

import SpringCA.entities.Semester;

import java.util.Set;

public interface AdminService {
    void reviewCourse(int studentId, int semesterId, int courseId, String action);
    void deleteStudent(int id);
    void deleteCourse(int id);
    void deleteLecturer(int id);
    Set<Semester> getSemestersForCourse(int id);

}

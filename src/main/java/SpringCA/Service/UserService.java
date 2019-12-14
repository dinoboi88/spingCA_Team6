package SpringCA.Service;

import SpringCA.entities.*;

public interface UserService {
    void setStudentUser(Student student);
    void setLecturerUser(Lecturer lecturer);
    void createPasswordResetTokenForStudent(StudentUser studentUser, String token);
    void createPasswordResetTokenForLecturer(LecturerUser lecturerUser, String token);
    void createPasswordResetTokenForAdmin(AdminUser adminUser, String token);
    void resetStudentPassword(String username, String password);
    void resetLecturerPassword(String username, String password);
    void resetAdminPassword(String username, String password);

}

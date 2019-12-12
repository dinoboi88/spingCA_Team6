package SpringCA.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Lecturer extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lecturerId;
    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department departmentLecturer;

    @OneToMany(mappedBy = "lecturerByCourse")
    private List<LecturerCourse> lecturemrCourses;

    @OneToMany(mappedBy = "lecturerByLeave")
    private List<LecturerLeave> lecturerLeaves;

    @OneToOne(mappedBy = "lecturerUser")
    private LecturerUser lecturerUser;

    public Lecturer(){}

    public Lecturer(String firstName, String middleName, String lastName, String gender, LocalDateTime birthDate,
                    String address, String email, String mobile, Department departmentLecturer) {
        super(firstName, middleName, lastName, gender, birthDate, address, email, mobile);
        this.departmentLecturer = departmentLecturer;
    }

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Department getDepartmentLecturer() {
        return departmentLecturer;
    }

    public void setDepartmentLecturer(Department departmentLecturer) {
        this.departmentLecturer = departmentLecturer;
    }
}

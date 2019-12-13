package SpringCA.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int semesterId;
    private String semesterLabel;

    @OneToMany(mappedBy = "semesterEnrolled")
    private List<Student> studentList;

    @OneToMany(mappedBy = "semesterStudentCourse")
    private List<StudentCourse> studentCourseListBySemester;

    @OneToMany(mappedBy = "semesterLecturerCourse")
    private List<LecturerCourse> lecturerCourseListBySemester;

    public Semester(){}

    public Semester(String semesterLabel) {
        this.semesterLabel = semesterLabel;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterLabel() {
        return semesterLabel;
    }

    public void setSemesterLabel(String semesterLabel) {
        this.semesterLabel = semesterLabel;
    }
    @Override
	public String toString() {
		return semesterLabel;
	}
}

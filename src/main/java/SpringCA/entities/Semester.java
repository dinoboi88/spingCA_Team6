package SpringCA.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int semesterId;
    private String semesterLabel;

    private Date startDate;
    private Date endDate;

    @OneToMany(mappedBy = "semesterEnrolled")
    private List<Student> studentList;

    @OneToMany(mappedBy = "semesterStudentCourse")
    private List<StudentCourse> studentCourseListBySemester;

    @OneToMany(mappedBy = "semesterLecturerCourse")
    private List<LecturerCourse> lecturerCourseListBySemester;

    public Semester(){}

    public Semester(String semesterLabel, Date startDate, Date endDate) {
        this.semesterLabel = semesterLabel;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

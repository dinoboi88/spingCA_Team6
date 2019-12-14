package SpringCA.entities;

import SpringCA.entities.CompositeId.StudentCourseId;

import javax.persistence.*;

@Entity
@IdClass(StudentCourseId.class)
public class StudentCourse {
    @Id
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student studentByCourse;

    @Id
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course courseByStudent;

    @Id
    @ManyToOne
    @JoinColumn(name = "semesterId")
    private Semester semesterStudentCourse;

    private String status;

    private float score;

    public StudentCourse(){}

    public StudentCourse(Student studentByCourse, Course courseByStudent, Semester semesterStudentCourse, float score, String status) {
        this.studentByCourse = studentByCourse;
        this.courseByStudent = courseByStudent;
        this.semesterStudentCourse = semesterStudentCourse;
        this.score = score;
        this.status = status;
    }

    public Student getStudentByCourse() {
        return studentByCourse;
    }

    public void setStudentByCourse(Student studentByCourse) {
        this.studentByCourse = studentByCourse;
    }

    public Course getCourseByStudent() {
        return courseByStudent;
    }

    public void setCourseByStudent(Course courseByStudent) {
        this.courseByStudent = courseByStudent;
    }

    public Semester getSemesterStudentCourse() {
        return semesterStudentCourse;
    }

    public void setSemesterStudentCourse(Semester semesterStudentCourse) {
        this.semesterStudentCourse = semesterStudentCourse;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

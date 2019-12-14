package SpringCA.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    @NotEmpty(message = "Course code cannot be blank")
    private String courseCode;
    @NotEmpty(message = "Course name cannot be blank")
    private String courseName;
    private int courseUnit;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department departmentCourse;

    @OneToMany(mappedBy = "courseByStudent")
    private List<StudentCourse> courseListByStudent;

    @OneToMany(mappedBy = "courseByLecturer")
    private List<LecturerCourse> lecturerCourseList;

    public Course(){}

    public Course(String courseCode, String courseName, int courseUnit, Department departmentCourse) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseUnit = courseUnit;
        this.departmentCourse = departmentCourse;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseUnit() {
        return courseUnit;
    }

    public void setCourseUnit(int courseUnit) {
        this.courseUnit = courseUnit;
    }

    public Department getDepartmentCourse() {
        return departmentCourse;
    }

    public void setDepartmentCourse(Department departmentCourse) {
        this.departmentCourse = departmentCourse;
    }
    
	@Override
	public String toString() {
		return "courseId: " + courseId + ", courseCode: " + courseCode + ", courseName: " + courseName;
	}
}

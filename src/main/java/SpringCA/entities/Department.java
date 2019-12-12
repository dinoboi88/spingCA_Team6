package SpringCA.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;
    private String departmentName;

    @OneToMany(mappedBy = "departmentCourse")
    private List<Course> courses;

    @OneToMany(mappedBy = "departmentLecturer")
    private List<Lecturer> lecturers;

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}

package SpringCA.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
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
    private List<LecturerCourse> lecturerCourses;

    @OneToMany(mappedBy = "lecturerByLeave")
    private List<LecturerLeave> lecturerLeaves;

    @OneToOne(mappedBy = "lecturerUser")
    private LecturerUser lecturerUser;

    public Lecturer(){}

    public Lecturer(String firstName, String middleName, String lastName, String gender, Date birthDate,
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
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lecturerId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lecturer other = (Lecturer) obj;
		if (lecturerId != other.lecturerId)
			return false;
		return true;
	}

}

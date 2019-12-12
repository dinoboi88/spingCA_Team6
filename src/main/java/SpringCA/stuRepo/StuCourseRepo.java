package SpringCA.stuRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SpringCA.entities.Course;
import SpringCA.entities.Semester;
import SpringCA.entities.Student;
import SpringCA.entities.StudentCourse;
import SpringCA.entities.CompositeId.StudentCourseId;

@Repository
public interface StuCourseRepo extends JpaRepository<StudentCourse, StudentCourseId>{

	List<StudentCourse> findByStudentByCourse(Student studentByCourse);
	List<StudentCourse> findByCourseByStudent(Course courseByStudent);
	List<StudentCourse> findBySemesterStudentCourse(Semester semesterStudentCourse);
	List<StudentCourse> findByStudentByCourseAndSemesterStudentCourse(Student studentByCourse, Semester semesterStudentCourse);
}

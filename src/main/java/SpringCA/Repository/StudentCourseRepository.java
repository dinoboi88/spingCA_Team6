package SpringCA.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SpringCA.entities.StudentCourse;

public interface StudentCourseRepository extends JpaRepository<StudentCourse,Integer> {

	@Query(value="select * from student_course where course_id=?1",nativeQuery=true)
	List<StudentCourse> findByCourseId(int courseid);
	
	@Query(value="select * from student_course where course_id=?1 and student_id=?2",nativeQuery=true)
	StudentCourse getStudentCourse(int courseid,int studid);
}

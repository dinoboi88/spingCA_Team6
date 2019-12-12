package SpringCA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SpringCA.entities.Course;

public interface CourseRepository extends JpaRepository<Course,Integer> {
	
	@Query(value="select * from course where course_id=?1",nativeQuery=true)
	Course findByCourseId(int courseid);

}

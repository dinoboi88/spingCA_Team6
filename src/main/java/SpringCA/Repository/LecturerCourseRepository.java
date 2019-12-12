package SpringCA.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SpringCA.entities.LecturerCourse;


public interface LecturerCourseRepository extends JpaRepository<LecturerCourse,Integer> {
	
	@Query(value="select * from lecturer_course where lecturer_id=?1",nativeQuery=true)
	List<LecturerCourse> findByLectId(int lectid);

}

package SpringCA.lecturerRepo;

import SpringCA.entities.Course;
import SpringCA.entities.Lecturer;
import SpringCA.entities.LecturerCourse;
import SpringCA.entities.Semester;
import SpringCA.entities.CompositeId.LecturerCourseId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

//Table with composite keys should use composite key as identifier. See example on github
//https://github.com/bhagyaj/composite-idclass/tree/master/src/main/java/com/bhagya

@Repository 
public interface LecturerCourseRepository extends JpaRepository<LecturerCourse, LecturerCourseId> {
    Iterable<LecturerCourse> findByCourseByLecturer_CourseId(int courseId);

    Iterable<LecturerCourse> findByCourseByLecturer_CourseIdAndSemesterLecturerCourse_SemesterId(int courseId, int SemesterId);

    Set<LecturerCourse> findByLecturerByCourse_LecturerId(int lecturerId);
    
	 Page<LecturerCourse> findAllByLecturerByCourse(Lecturer lec,Pageable pageable);
	 
	 Page<LecturerCourse> findAllByLecturerByCourseAndCourseByLecturer(Lecturer lec,Course course,Pageable pageable);
	 
	 Page<LecturerCourse> findAllByLecturerByCourseAndSemesterLecturerCourse(Lecturer lec,Semester sem,Pageable pageable);
}
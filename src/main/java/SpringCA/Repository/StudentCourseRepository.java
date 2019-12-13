package SpringCA.Repository;

import SpringCA.entities.Course;
import SpringCA.entities.Semester;
import SpringCA.entities.StudentCourse;
import SpringCA.entities.CompositeId.StudentCourseId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//Table with composite keys should use composite key as identifier. See example on github
// https://github.com/bhagyaj/composite-idclass/tree/master/src/main/java/com/bhagya
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
    Iterable<StudentCourse> findByCourseByStudent_CourseId(int courseId);

    Iterable<StudentCourse> findByCourseByStudent_CourseIdAndSemesterStudentCourse_SemesterIdAndStatus(int courseId, int semesterId, String status);

    Iterable<StudentCourse> findByStudentByCourse_StudentId(int studentId);
    
    public Page<StudentCourse> findAllByCourseByStudentAndSemesterStudentCourse(Course course,Semester sem,Pageable pageable);
    
}

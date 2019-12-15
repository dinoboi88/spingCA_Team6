package SpringCA.Repository;

import SpringCA.entities.CompositeId.StudentCourseId;
import SpringCA.entities.Course;
import SpringCA.entities.Semester;
import SpringCA.entities.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
    Iterable<StudentCourse> findByCourseByStudent_CourseId(int courseId);

    Page<StudentCourse> findByCourseByStudent_CourseIdAndSemesterStudentCourse_SemesterIdAndStatus(
            int courseId, int semesterId, String status, Pageable pageable);

    Iterable<StudentCourse> findByCourseByStudent_CourseIdAndSemesterStudentCourse_SemesterIdAndStatus(
            int courseId, int SemesterId, String status);

    Iterable<StudentCourse> findByStudentByCourse_StudentId(int studentId);

    Iterable<StudentCourse> findByStudentByCourse_StudentIdAndStatus(int studentId, String status);

    Page<StudentCourse> findBySemesterStudentCourse_SemesterIdAndStatus(int semesterId, String status, Pageable pageable);

    List<StudentCourse> findBySemesterStudentCourse_SemesterIdAndStatus(int semesterId, String status);
      
    public Page<StudentCourse> findAllByCourseByStudentAndSemesterStudentCourse(Course course,Semester sem,Pageable pageable);

}

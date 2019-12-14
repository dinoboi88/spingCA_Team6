package SpringCA.Repository;

import SpringCA.entities.CompositeId.StudentCourseId;
import SpringCA.entities.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {
    Iterable<StudentCourse> findByCourseByStudent_CourseId(int courseId);

    Page<StudentCourse> findByCourseByStudent_CourseIdAndSemesterStudentCourse_SemesterIdAndStatus(
            int courseId, int semesterId, String status, Pageable pageable);

    Iterable<StudentCourse> findByCourseByStudent_CourseIdAndSemesterStudentCourse_SemesterIdAndStatus(
            int courseId, int SemesterId, String status);

    Iterable<StudentCourse> findByStudentByCourse_StudentId(int studentId);

    Iterable<StudentCourse> findByStudentByCourse_StudentIdAndStatus(int studentId, String status);

    Iterable<StudentCourse> findBySemesterStudentCourse_SemesterIdAndStatus(int semesterId, String status);

}
